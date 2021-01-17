package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Adapters.BooksListViewAdapter;
import com.example.vasos.bookstoreapp.Helpers.AppConfig;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class AllBooks extends Activity {
    private Toolbar actionBarToolbar;
    private ArrayList<Book> allBooksAvailable,usersBoughtBooks = new ArrayList<>();
    private ArrayList<Book> filteredSearchBookList = new ArrayList<>();
    private ListView booksListView;
    private Context context;
    private boolean isMyBooksView;
    private TextView allBooksActivityTitleTextView;
    private SearchView bookSearchView;
    private BooksListViewAdapter booksListViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        context = this;
        setViews();

        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent backToMain = new Intent(AllBooks.this,MainActivity.class);
                startActivity(backToMain);
            }
        });

        allBooksAvailable = MainActivity.getAllBooks();
        booksListViewAdapter = new BooksListViewAdapter(this,0,allBooksAvailable);

        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getBoolean("MyBooksIntent"))
            {
                isMyBooksView = true;
                allBooksActivityTitleTextView.setText("my Books");
                booksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,appUser.getUserBooksBought());
                booksListView.setAdapter(booksListViewAdapter);
                booksListViewAdapter.notifyDataSetChanged();
                booksListViewAdapter.setMyBooks(true);
                //getBuyedBooks(String.valueOf(appUser.getAppUserId()));
            }
        }
        else
        {
            booksListView.setAdapter(booksListViewAdapter);
            booksListViewAdapter.notifyDataSetChanged();
            booksListViewAdapter.setMyBooks(false);
        }
        setSearchFuntion();
    }

    /**
     * function to verify login details in mysql db
     * */
    private void getBuyedBooks(final String userId) {
        new JsonTask().execute(AppConfig.URL_BOOKS_BUYED+userId);
        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void setSearchFuntion()
    {
        bookSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                if(!s.isEmpty()&&s.length()>0)
                {
                    filteredSearchBookList.clear();
                    if(!isMyBooksView)
                    {
                        for(int i = 0;i<allBooksAvailable.size();i++)
                        {
                            if(allBooksAvailable.get(i).getBookTitle().toLowerCase().contains(s.toLowerCase()) || allBooksAvailable.get(i).getBookGenre().toLowerCase().contains(s.toLowerCase()))
                            {
                                filteredSearchBookList.add(allBooksAvailable.get(i));
                            }
                        }
                        BooksListViewAdapter filteredBooksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,filteredSearchBookList);
                        filteredBooksListViewAdapter.setMyBooks(isMyBooksView);
                        booksListView.setAdapter(filteredBooksListViewAdapter);
                        filteredBooksListViewAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        if(appUser.getUserBooksBought().size()>0)
                        for(int i = 0;i<appUser.getUserBooksBought().size();i++)
                        {
                            if(appUser.getUserBooksBought().get(i).getBookTitle().toLowerCase().contains(s.toLowerCase()) || appUser.getUserBooksBought().get(i).getBookGenre().toLowerCase().contains(s.toLowerCase()))
                            {
                                filteredSearchBookList.add(appUser.getUserBooksBought().get(i));
                            }
                        }
                        BooksListViewAdapter filteredBooksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,filteredSearchBookList);
                        filteredBooksListViewAdapter.setMyBooks(isMyBooksView);
                        booksListView.setAdapter(filteredBooksListViewAdapter);
                        filteredBooksListViewAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    BooksListViewAdapter booksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,allBooksAvailable);
                    booksListView.setAdapter(booksListViewAdapter);
                    booksListViewAdapter.setMyBooks(isMyBooksView);
                    booksListViewAdapter.notifyDataSetChanged();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });

        bookSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose()
            {
                if(isMyBooksView)
                {
                    BooksListViewAdapter booksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,appUser.getUserBooksBought());
                    booksListViewAdapter.setMyBooks(isMyBooksView);
                    booksListView.setAdapter(booksListViewAdapter);
                    booksListViewAdapter.notifyDataSetChanged();
                }
                else
                {
                    BooksListViewAdapter booksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,allBooksAvailable);
                    booksListViewAdapter.setMyBooks(isMyBooksView);
                    booksListView.setAdapter(booksListViewAdapter);
                    booksListViewAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });
    }

    private void setViews()
    {
        booksListView = (ListView) findViewById(R.id.booksListView);
        allBooksActivityTitleTextView = (TextView) findViewById(R.id.allBooksActivityTitleTextView);
        actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        bookSearchView = (SearchView) findViewById(R.id.bookSearchView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarBooks);
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

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                JSONObject jObject = new JSONObject(buffer.toString());
                JSONArray jArray = jObject.getJSONArray("data");
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String bookid = oneObject.getString("book_id");
                        usersBoughtBooks.add(MainActivity.allBooks.get(Integer.parseInt(bookid)));
                        System.out.println("bookid" + bookid + " int value " +Integer.parseInt(bookid));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            booksListViewAdapter = new BooksListViewAdapter(getApplicationContext(),0,usersBoughtBooks);
            booksListViewAdapter.setMyBooks(isMyBooksView);
            booksListView.setAdapter(booksListViewAdapter);
            booksListViewAdapter.notifyDataSetChanged();
            appUser.setUserBooksBought(usersBoughtBooks);
            progressBar.setVisibility(View.GONE);
        }
    }

}