package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.Helpers.AppConfig;
import com.example.vasos.bookstoreapp.Helpers.AppController;
import com.example.vasos.bookstoreapp.Helpers.SessionManager;
import com.example.vasos.bookstoreapp.Models.AppUser;
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
import java.util.HashMap;
import java.util.Map;

import static com.example.vasos.bookstoreapp.Activities.BookView.lastOpenedBookName;
import static com.example.vasos.bookstoreapp.Helpers.AppController.TAG;

public class MainActivity extends Activity {
    private Button logoutButton;
    private Button viewAllBooksButton;
    private Button myBooksButton;
    private Button continueReadingButton;
    private ImageView booksImageView;
    public static String bookToReadFileTitle;
    public static String booksImageUrl = "https://ashmagautam.files.wordpress.com/2013/11/mcj038257400001.jpg";
    private TextView numberOfBooksTextView,welcomeTextView;
    public static int appUserId = -1;
    public static AppUser appUser ;
    private SessionManager session;
    private ArrayList<Book> myBooks = new ArrayList<>();
    public static ArrayList<Book> allBooks = new ArrayList<>();
    private Context context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setViews();
        if(appUser!=null)
        {
            appUser.setUserLoggedIn(true);
            if(appUser.getAppUserName()!=null)
            {
                welcomeTextView.setText("Hello " + appUser.getAppUserName());
                appUserId = appUser.getAppUserId();
            }
        }
        loadAllBooks();

    }

    private void setViews()
    {
        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewAllBooksButton = (Button) findViewById(R.id.viewAllAvailableBooksButton);
        myBooksButton = (Button) findViewById(R.id.myBooksButton);
        continueReadingButton = (Button) findViewById(R.id.continueReadingButton);
        booksImageView = (ImageView) findViewById(R.id.booksImageView);
        numberOfBooksTextView = (TextView) findViewById(R.id.numberOfBooksTextView);
        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    }

    public void loadUsersBooksBought()
    {
        myBooks  = new ArrayList<>();

        if(appUser!=null && appUserId>=0)
        {

            new JsonTask().execute(AppConfig.URL_BOOKS_BUYED + appUserId);
        }
    }

    private void loadAllBooks()
    {
        allBooks.clear();

        getbooks();

      /*  Book book = new Book(1,"Frankenstein","Shelley Mary",
                "The 1818 edition of Frankenstein. This version is based on a digitisation by Distributed Proofreaders cross checked against an existing Project Gutenberg text and a new DP digitisation of the 1831 edition. Frankenstein; or, The Modern Prometheus is a novel written by English author Mary Shelley (1797–1851) that tells the story of Victor Frankenstein, a young scientist who creates a hideous sapient creature in an unorthodox scientific experiment.",
                "Science Fiction","https://archive.org/download/Frankenstein1818Edition/frank-a5.pdf",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Frankenstein.1831.inside-cover.jpg/800px-Frankenstein.1831.inside-cover.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(2,"Philosophy of natural therapeutics","Lindlahr Henry",
                "Originating in Europe in the early twentieth century, the Nature Cure movement laid the foundations for much of complementary medicine and naturopathy as we know it today. Dr Henry Lindlahr was one of the pioneers of Nature Cure who popularised the movement in the West. Eloquent and passionate about his theories, in this ground-breaking book Dr Lindlahr outlines the principles and practical applications of Nature Cure, to create a comprehensive philosophy of natural therapeutics.",
                "Healing/Physical Therapy","https://archive.org/download/philosophyofnatu00lind/philosophyofnatu00lind.pdf",
                "https://images-na.ssl-images-amazon.com/images/I/51NUN0D6WmL._SX331_BO1,204,203,200_.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(3,"The autobiography of Benjamin Franklin","Benjamin Franklin",
                "Franklin's account of his life is divided into four parts, reflecting the different periods at which he wrote them. There are actual breaks in the narrative between the first three parts, but Part Three's narrative continues into Part Four without an authorial break.",
                "Autobiography","https://archive.org/download/autobiobenfran00miffrich/autobiobenfran00miffrich.pdf",
                "https://upload.wikimedia.org/wikipedia/commons/0/04/Memoirs_of_Franklin.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(4,"A guide to the history of science; a first guide for the study of the history of science, with introductory essays on science and tradition","Santon George",
                "An authentic and of the very first gudes to History of Science teaching in modern times with an exhaustive,classified bibiliography on history of science.",
                "Science/History","https://archive.org/download/guidetohistoryof00sart/guidetohistoryof00sart.pdf",
                "https://ia800203.us.archive.org/BookReader/BookReaderPreview.php?id=guidetohistoryof00sart&itemPath=%2F14%2Fitems%2Fguidetohistoryof00sart&server=ia800203.us.archive.org&page=cover_t.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(5,"The nature of the physical world",
                "Sir Arthur Stanley Eddington",
                "In chapters 1–11, Eddington explains, in non-technical and sometimes entertaining language, the main ideas of modern physics as it stood in 1927. " +
                        "In chapters 12–15, he then discuses what he considers their main philosophical and religious implications.",
                "Science/Philosophy","https://archive.org/download/natureofphysical00eddi/natureofphysical00eddi.pdf",
                "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1387439281l/1682216.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(6,"Our surroundings; an elementary general science","Clement, Arthur G. Collister, Morton Christian, Thurston, E. L. (Ernest Lawton)",
                "The immediate aim of Our Surroundings is to place the pupil in tune with the common things about him, giving him an understanding and appreciation of, and an interest in, his environment."
                        + "Our Surroundings draws upon many sciences to bring about this contact and understanding. With the help of these sciences it " +
                        "opens the door through which the pupil sees the true importance of the familiar and common things about him. It does more. It fires the pupil's interest, " +
                        "awakening the desire to go farther afield and to explore in more detail the territory of some particular science. It gives the preliminary view and contact that make possible " +
                        "an intelligent choice of the sciences for more extensive and intensive study in later school years.",
                "Science","https://archive.org/download/oursurroundingse00clemrich/oursurroundingse00clemrich.pdf",
                "https://archive.org/services/img/oursurroundingse00clemrich");
        book.setBookPrice(10);
        allBooks.add(book);
        book = new Book(7,"Science, religion and reality","Joseph Needham, Arthur James Balfour",
                "Essays on science, religion, and reality by various authors. Contents: Introduction; Magic Science and Religion; Historical relations of religion and science; Science and religion in the nineteenth century, The domain of physical science; Mechanistic biology and the religious consciousness; " +
                        "Sphere of religion; Religion and psychology; Science Christianity and Modern Civilization",
                "Science/Religion","https://archive.org/download/sciencereligionr00need/sciencereligionr00need.pdf",
                "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1348978845l/149798.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(8,"Science fiction and the prediction of the future : essays on foresight and fallacy","Gary Westfahl, Wong Kin Yuen, Amy Kit-sze Chan",
                "Science fiction has always intrigued readers with depictions of an unforeseen future. Can the genre actually provide audiences with a glance into the world of tomorrow? " +
                        "This collection of fifteen international and interdisciplinary essays examines the genre's predictions and breaks new ground by considering the prophetic functions of science fiction films," +
                        " as well as science fiction literature.",
                "Science/Fiction","https://archive.org/download/" +
                "Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy/Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy_by_Gary_Westfahl_and_Donald_E._Palumbo.pdf",
                "https://q9f3r2c3.rocketcdn.me/mcwp17/wp-content/uploads/978-0-7864-5841-7-1.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(9,"The survivor","Tom Cain",
                "The novel opens with Samuel Carver masquerading as a maintenance man. He sabotages the executive jet of wealthy Texan businessman Waylon McCabe. " +
                        "The sabotage fails and McCabe begins to suspect that he was the target of an assassination as opposed to a victim of a freak accident. The novel then jumps forward to continue" +
                        " the story of Cain's first novel, The Accident Man. Carver is recovering in a Swiss hospital and attempting to regain memories lost during the torture by that book's villain.The story centres around McCabe's attempt to obtain a lost Russian suitcase nuke in an effort to instigate a nuclear holocaust that would bring about the rapture; Carver aims to stop him.",
                "Nuclear terrorism","https://archive.org/download/survivor00corg/survivor00corg.pdf",
                "https://upload.wikimedia.org/wikipedia/en/thumb/6/64/The_Survivor.jpg/220px-The_Survivor.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(10,"Dracula","Bram Stoker",
                "Dracula is an 1897 Gothic horror novel by Irish author Bram Stoker. It introduced the character of Count Dracula and established many conventions of subsequent vampire fantasy. The novel tells the story of Dracula's " +
                        "attempt to move from Transylvania to England so that he may find new blood and spread the undead curse, and of the battle between Dracula and a small group of people led by Professor Abraham Van Helsing. ",
                "Horror/Gothic","https://archive.org/download/draculabr00stokuoft/draculabr00stokuoft.pdf",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Dracula1st.jpeg/200px-Dracula1st.jpeg");

        book.setBookPrice(10);
        allBooks.add(book);*/
        //loadUsersBooksBought();//!!!
    }

    /**
     * function to load all books from database through api call
     * */
    private void getbooks() {
        // Tag used to cancel the request
        String tag_string_req = "req_books";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SEARCH_BOOK_INFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        JSONObject jObject = new JSONObject(response.toString());
                        JSONArray jArray = jObject.getJSONArray("data");
                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String bookid = oneObject.getString("id");
                                String title = oneObject.getString("tittle");
                                String description = oneObject.getString("description");
                                String author = oneObject.getString("author");
                                String download_url = oneObject.getString("download_url");
                                String image_url = oneObject.getString("image_url");
                                String category = oneObject.getString("category");

                                Book book1   = new Book(Integer.parseInt(bookid),title,author,description,category,download_url,image_url);
                                book1.setBookPrice(10);
                                allBooks.add(book1);
                                System.out.println("bookid" + bookid + " int value " +Integer.parseInt(bookid));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadUsersBooksBought();
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
       /*         params.put("email", email);
                params.put("password", password);*/
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        //new DownloadImageTask(booksImageView).execute(booksImageUrl);
        Glide.with(context).load(booksImageUrl).into(booksImageView);
        session = new SessionManager(getApplicationContext());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exitApp = new Intent(MainActivity.this,Login.class);
                exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exitApp);

                session.setLogin(false);
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
                if(lastOpenedBookName!="")
                {
                    Intent continueReading = new Intent(MainActivity.this,BookView.class);
                    continueReading.putExtra("lastOpenedBookName",lastOpenedBookName);
                    startActivity(continueReading);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Sorry no last Opened Book Name found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        session.setLogin(true);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                session.setLogin(true);
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                break;
        }
        return true;
    }

    public static ArrayList<Book> getAllBooks()
    {
        return allBooks;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
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
                        myBooks.add(MainActivity.allBooks.get(Integer.parseInt(bookid)-1));
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
            appUser.setUserBooksBought(myBooks);
            appUser.setAppUserNoOfBooks(myBooks.size());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(appUser!=null)
                    {
                        numberOfBooksTextView.setText(String.valueOf(appUser.getAppUserNoOfBooks()));
                    }
                }
            });

        }
    }
}
