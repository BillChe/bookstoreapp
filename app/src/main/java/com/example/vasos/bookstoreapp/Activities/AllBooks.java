package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Adapters.BooksListViewAdapter;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.util.ArrayList;

public class AllBooks extends Activity {
    private TextView idTextView;

    private Button logoutButton;
    private Toolbar actionBarToolbar;
    private int allBooksAvailable = 3;
    private int booksBought;
    private ListView booksListView;
    private Context context;
    private boolean isMyBooksView;
    private TextView allBooksActivityTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        context = this;
        idTextView = (TextView) findViewById(R.id.idTextView);
        idTextView.setPaintFlags(idTextView.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        logoutButton  = (Button) findViewById(R.id.logoutButton);
        booksListView = (ListView) findViewById(R.id.booksListView);
        allBooksActivityTitleTextView = (TextView) findViewById(R.id.allBooksActivityTitleTextView);

        try
        {
            if(getIntent().getExtras().getBoolean("MyBooksIntent"))
            {
                allBooksActivityTitleTextView.setText("my Books");
            }
        }
        catch (Exception e){

        }

        actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent backToMain = new Intent(AllBooks.this,MainActivity.class);
                startActivity(backToMain);
            }
        });

        ArrayList<Book> books = new ArrayList<>();

        for(int i = 0; i< allBooksAvailable; i++)
        {
            books.add(new Book(i,"title " + i,"","",""));
        }

        BooksListViewAdapter booksListViewAdapter = new BooksListViewAdapter(this,books);

        booksListView.setAdapter(booksListViewAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exitApp = new Intent(AllBooks.this,Login.class);
                exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exitApp);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent exitApp = new Intent(AllBooks.this,MainActivity.class);
        //exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(exitApp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent exitApp = new Intent(AllBooks.this,MainActivity.class);
                startActivity(exitApp);
                break;
        }
        return true;
    }

}
