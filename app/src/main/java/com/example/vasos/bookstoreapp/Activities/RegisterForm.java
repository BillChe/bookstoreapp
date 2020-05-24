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

import com.example.vasos.bookstoreapp.Models.AppUser;
import com.example.vasos.bookstoreapp.R;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class RegisterForm extends Activity {
    private Button registerFormButton;
    private Context context;
    private EditText usernameEditText,passwordEditText, emailEditText;
    boolean registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_form);

        usernameEditText = findViewById(R.id.usernameET);
        passwordEditText = findViewById(R.id.passwordET);
        emailEditText = findViewById(R.id.emailET);

        context = this;
        registerFormButton = (Button) findViewById(R.id.registerFormButton);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if((usernameEditText.getText()!=null) && (passwordEditText.getText()!=null))
                {
                    appUser = new AppUser(0,usernameEditText.getText().toString(),passwordEditText.getText().toString(),0, true);
/*
                    SQLiteDbHelper sqLiteDbHelper= new SQLiteDbHelper(context);
                    sqLiteDbHelper.insertUser(appUser);*/


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
                }

                registered = true;



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
}
