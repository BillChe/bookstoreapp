package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.DB.SQLiteDbHelper;
import com.example.vasos.bookstoreapp.Helpers.AppConfig;
import com.example.vasos.bookstoreapp.Helpers.AppController;
import com.example.vasos.bookstoreapp.Helpers.SessionManager;
import com.example.vasos.bookstoreapp.Models.AppUser;
import com.example.vasos.bookstoreapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;
import static com.example.vasos.bookstoreapp.Helpers.AppController.TAG;

public class Login extends Activity {
    private Button loginBtn,registerBtn;
    private TextView registerHereText;
    private EditText username, password;
    private String usernameStr,passwordStr = "";
    private int id = -1;
    private LinearLayout linearLayout;
    private Context context;
    private ImageView booksImageView;
    private static String booksImageUrl = "https://images.pexels.com/photos/51342/books-education-school-literature-51342.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
    private SessionManager session;
    private SQLiteDbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        context = this;

        loginBtn = (Button) findViewById(R.id.loginButton);
        registerBtn  = (Button) findViewById(R.id.registerButton);
        registerHereText = (TextView) findViewById(R.id.registerHereTV);
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutLogin);
        booksImageView = (ImageView) findViewById(R.id.booksImageViewLogin);

        // SQLite database handler
        db = new SQLiteDbHelper(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());



        try
        {
            Glide.with(this).load(booksImageUrl).into(booksImageView);
        }
        catch (Exception e)
        {}

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent loginIntent = new Intent(Login.this,RegisterForm.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameStr = username.getText().toString().trim();
                passwordStr = password.getText().toString().trim();
                if(!usernameStr.isEmpty() && !passwordStr.isEmpty())
                {
                    checkLogin(usernameStr, passwordStr);
                }
                else
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Missing Field")
                            .setMessage("Please enter your username and password")
                            .setCancelable(false)
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    dialog.dismiss();
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            appUser = new AppUser(id,session.getUsername(),passwordStr,0, true);
            appUser.setAppUserName(session.getUsername());
            appUser.setAppUserId(Integer.parseInt(session.getUserId()));
            db.insertUser(appUser);
            intentToMain();
        }
    }

    public void intentToMain()
    {
        Intent loginIntent = new Intent(Login.this,MainActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        JSONObject user = jObj.getJSONObject("user_info");
                        // Now store the user in SQLite
                        String uid = user.getString("id");
                        String email = user.getString("email");
                        String username = user.getString("username");
                        session.setUserInfo(uid,username);
                        // Inserting row in users table
                        id = Integer.parseInt(uid);
                        appUser = new AppUser(id,username,password,0, true);
                        db.insertUser(appUser);


                        // Launch main activity
                       intentToMain();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

}