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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import static android.support.constraint.Constraints.TAG;
import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class RegisterForm extends Activity {
    private Button registerFormButton;
    private Context context;
    private EditText usernameEditText,passwordEditText, emailEditText,nameEditText;
    boolean registered = false;
    private SessionManager session;
    private SQLiteDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_form);
        // Session manager
        session = new SessionManager(getApplicationContext());

        usernameEditText = findViewById(R.id.usernameET);
        passwordEditText = findViewById(R.id.passwordET);
        emailEditText = findViewById(R.id.emailET);
        nameEditText = findViewById(R.id.nameET);

        context = this;
        registerFormButton = (Button) findViewById(R.id.registerFormButton);
        db = new SQLiteDbHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((usernameEditText.getText().length()>=6) && (passwordEditText.getText().length()>=6) && (nameEditText.getText().length()>=6))
                {
                    String username = usernameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String email = emailEditText.getText().toString().trim();
                    String name = nameEditText.getText().toString().trim();

                    registerUser(username, password, email, name);


                }
                else
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Not Completed")
                            .setMessage("Check your credentials")
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

        if(registered)
        {
            Intent loginIntent = new Intent(this,Login.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            this.finish();
        }

    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String username, final String password, final String email, final String name) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("username");
                        String uid = user.getString("id");
                        String email = user.getString("email");
                        String password = user.getString("password");

                        appUser = new AppUser(0,username,password,0, true);
                        // Inserting row in users table
                        db.insertUser(appUser);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        registered = true;
                        new AlertDialog.Builder(context)
                                .setTitle("Completed entry")
                                .setMessage("You Are Now Registered")
                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        dialog.dismiss();
                                        Intent loginIntent = new Intent(RegisterForm.this,Login.class);
                                        startActivity(loginIntent);
                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                        // Launch login activity

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", name);
                params.put("username", username);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
