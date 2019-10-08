package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.R;

public class SingleBookDescription extends Activity {
    private Button readButton;
    private Button backToAllBooksButton;
    private Button addToMyBooksButton;
    public static String bookToReadFileTitle;
    private Toolbar actionBarToolbar;
    private Context context;
    private TextView bookTitleDescriptionTextView,bookDescriptionTextTextView;
    private static boolean isAddedToBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_single_book_description);
        context = this;

        bookToReadFileTitle = BookView.bookFileTitle;
        readButton = (Button) findViewById(R.id.readButton);
        backToAllBooksButton = (Button) findViewById(R.id.backToAllBooksButton);
        addToMyBooksButton = (Button) findViewById(R.id.addToMyBooksButton);
        actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_description_view);
        bookTitleDescriptionTextView = (TextView) findViewById(R.id.bookTitleDescriptionTextView);
        bookDescriptionTextTextView = (TextView) findViewById(R.id.bookDescriptionTextTextView);

        bookTitleDescriptionTextView.setText(bookToReadFileTitle);
        bookDescriptionTextTextView.setText("Description of : " + bookToReadFileTitle);

        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent backToAllBooks = new Intent(SingleBookDescription.this,AllBooks.class);
                startActivity(backToAllBooks);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isAddedToBooks){
            readButton.setVisibility(View.INVISIBLE);

        }
        else{
            addToMyBooksButton.setVisibility(View.INVISIBLE);
        }

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readBook = new Intent(SingleBookDescription.this,BookView.class);
                startActivity(readBook);
            }
        });
        backToAllBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
                //goBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goBack);
            }
        });
        addToMyBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Successfully added to your books!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                                addToMyBooksButton.setVisibility(View.INVISIBLE);
                                readButton.setVisibility(View.VISIBLE);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                isAddedToBooks = true;
                MainActivity.appUser.setAppUserNoOfBooks(MainActivity.appUser.getAppUserNoOfBooks() + 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
        //goBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goBack);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
                startActivity(goBack);
                break;
        }
        return true;
    }
}
