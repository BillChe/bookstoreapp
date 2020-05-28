package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class Login extends Activity {
    private Button loginBtn,registerBtn;
    private TextView registerHereText;
    private EditText username;
    private EditText password;
    private String usernameStr,passwordStr = "";
    private LinearLayout linearLayout;
    private Context context;
    private ImageView booksImageView;
    private static String booksImageUrl = "https://images.pexels.com/photos/51342/books-education-school-literature-51342.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";

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


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(appUser != null)
        {
            if(appUser.isIsUserLoggedIn())
            {

                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usernameStr = username.getText().toString();
                        passwordStr = password.getText().toString();
                        if(!usernameStr.equals("") && !passwordStr.equals(""))
                        {
                            if((usernameStr.equals(appUser.getAppUserName())) && (passwordStr.equals(appUser.getAppUserPassword())))
                            {
                                Intent loginIntent = new Intent(Login.this,MainActivity.class);

                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginIntent);
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
        }

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