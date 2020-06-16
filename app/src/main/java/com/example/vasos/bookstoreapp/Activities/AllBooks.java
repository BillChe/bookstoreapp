package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Adapters.BooksListViewAdapter;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.util.ArrayList;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class AllBooks extends Activity {
    private TextView idTextView;
    private Toolbar actionBarToolbar;
    private ArrayList<Book> allBooksAvailable,usersBoughtBooks = new ArrayList<>();
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
        booksListView = (ListView) findViewById(R.id.booksListView);
        allBooksActivityTitleTextView = (TextView) findViewById(R.id.allBooksActivityTitleTextView);
        actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent backToMain = new Intent(AllBooks.this,MainActivity.class);
                startActivity(backToMain);
            }
        });

        allBooksAvailable = MainActivity.getAllBooks();
        BooksListViewAdapter booksListViewAdapter = new BooksListViewAdapter(this,0,allBooksAvailable);

        booksListView.setAdapter(booksListViewAdapter);
        booksListViewAdapter.notifyDataSetChanged();

        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getBoolean("MyBooksIntent"))
            {
                isMyBooksView = true;
                allBooksActivityTitleTextView.setText("my Books");
                booksListViewAdapter = new BooksListViewAdapter(this,0,appUser.getUserBooksBought());
                booksListViewAdapter.setMyBooks(isMyBooksView);
                booksListView.setAdapter(booksListViewAdapter);
                booksListViewAdapter.notifyDataSetChanged();


            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent exitApp = new Intent(AllBooks.this,MainActivity.class);
        exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
