package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Helpers.DownloadImageTask;
import com.example.vasos.bookstoreapp.Models.AppUser;
import com.example.vasos.bookstoreapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class MainActivity extends Activity {
    private Button logoutButton;
    private Button viewAllBooksButton;
    private Button myBooksButton;
    private Button continueReadingButton;
    private ImageView booksImageView;
    public static String bookToReadFileTitle;
    private Bitmap bmp;
    private static String booksImageUrl = "https://images.pexels.com/photos/51342/books-education-school-literature-51342.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
    private TextView numberOfBooksTextView;
    public static int appUserId = 0;
    public static AppUser appUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        appUser.setIsUserLoggedIn(true);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewAllBooksButton = (Button) findViewById(R.id.viewAllAvailableBooksButton);
        myBooksButton = (Button) findViewById(R.id.myBooksButton);
        continueReadingButton = (Button) findViewById(R.id.continueReadingButton);
        booksImageView = (ImageView) findViewById(R.id.booksImageView);
        numberOfBooksTextView = (TextView) findViewById(R.id.numberOfBooksTextView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new DownloadImageTask(booksImageView).execute(booksImageUrl);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exitApp = new Intent(MainActivity.this,Login.class);
                exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exitApp);
            }
        });

        viewAllBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAllBooks = new Intent(MainActivity.this,AllBooks.class);
                //viewAllBooks.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(viewAllBooks);
            }
        });

        myBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myBooks = new Intent(MainActivity.this,AllBooks.class);
                //myBooks.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                myBooks.putExtra("MyBooksIntent",true);
                startActivity(myBooks);
            }
        });

        continueReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueReading = new Intent(MainActivity.this,BookView.class);
                //myBooks.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(continueReading);
            }
        });

        String appUserNoOfBooks = String.valueOf(appUser.getAppUserNoOfBooks());
        numberOfBooksTextView.setText(appUserNoOfBooks);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent exitApp = new Intent(MainActivity.this,Login.class);
        //exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(exitApp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent exitApp = new Intent(MainActivity.this,Login.class);
                startActivity(exitApp);
                break;
        }
        return true;
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
