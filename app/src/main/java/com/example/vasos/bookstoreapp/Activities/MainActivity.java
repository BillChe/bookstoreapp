package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.Helpers.SessionManager;
import com.example.vasos.bookstoreapp.Models.AppUser;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static com.example.vasos.bookstoreapp.Activities.BookView.lastOpenedBookName;

public class MainActivity extends Activity {
    private Button logoutButton;
    private Button viewAllBooksButton;
    private Button myBooksButton;
    private Button continueReadingButton;
    private ImageView booksImageView;
    public static String bookToReadFileTitle;
    private Bitmap bmp;
    public static String booksImageUrl = "https://ashmagautam.files.wordpress.com/2013/11/mcj038257400001.jpg";
    private TextView numberOfBooksTextView,welcomeTextView;
    public static int appUserId = 0;
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
            }

        }
        loadAllBooks();
        loadUsersBooksBought();

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

    private void loadUsersBooksBought()
    {
        myBooks  = new ArrayList<>();
        if(appUser!=null)
        {
            appUser.setUserBooksBought(myBooks);
            appUser.setAppUserNoOfBooks(myBooks.size());
        }


    }

    private void loadAllBooks()
    {
        allBooks.clear();

        Book book = new Book(1,"Frankenstein","Shelley Mary",
                "The 1818 edition of Frankenstein. This version is based on a digitisation by Distributed Proofreaders cross checked against an existing Project Gutenberg text and a new DP digitisation of the 1831 edition. Frankenstein; or, The Modern Prometheus is a novel written by English author Mary Shelley (1797–1851) that tells the story of Victor Frankenstein, a young scientist who creates a hideous sapient creature in an unorthodox scientific experiment.",
                "Science Fiction","https://archive.org/download/Frankenstein1818Edition/frank-a5.pdf",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Frankenstein.1831.inside-cover.jpg/800px-Frankenstein.1831.inside-cover.jpg");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(2,"Philosophy of natural therapeutics","Lindlahr Henry",
                "Originating in Europe in the early twentieth century, the Nature Cure movement laid the foundations for much of complementary medicine and naturopathy as we know it today. Dr Henry Lindlahr was one of the pioneers of Nature Cure who popularised the movement in the West. Eloquent and passionate about his theories, in this ground-breaking book Dr Lindlahr outlines the principles and practical applications of Nature Cure, to create a comprehensive philosophy of natural therapeutics.",
                "Healing/Physical Therapy","https://archive.org/download/philosophyofnatu00lind/philosophyofnatu00lind.pdf",
                "https://ia802607.us.archive.org/BookReader/BookReaderImages.php?zip=/11/items/philosophyofnatu00lind/philosophyofnatu00lind_jp2.zip&file=philosophyofnatu00lind_jp2/philosophyofnatu00lind_0001.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(3,"The autobiography of Benjamin Franklin","Benjamin Franklin",
                "Franklin's account of his life is divided into four parts, reflecting the different periods at which he wrote them. There are actual breaks in the narrative between the first three parts, but Part Three's narrative continues into Part Four without an authorial break.",
                "Autobiography","https://archive.org/download/autobiobenfran00miffrich/autobiobenfran00miffrich.pdf",
                "https://ia802604.us.archive.org/BookReader/BookReaderImages.php?zip=/26/items/autobiobenfran00miffrich/autobiobenfran00miffrich_jp2.zip&file=autobiobenfran00miffrich_jp2/autobiobenfran00miffrich_0001.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(4,"A guide to the history of science; a first guide for the study of the history of science, with introductory essays on science and tradition","Santon George",
                "An authentic and of the very first gudes to History of Science teaching in modern times with an exhaustive,classified bibiliography on history of science.",
                "Science/History","https://archive.org/download/guidetohistoryof00sart/guidetohistoryof00sart.pdf",
                "https://ia800203.us.archive.org/BookReader/BookReaderImages.php?zip=/14/items/guidetohistoryof00sart/guidetohistoryof00sart_jp2.zip&file=guidetohistoryof00sart_jp2/guidetohistoryof00sart_0001.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(5,"The nature of the physical world",
                "Sir Arthur Stanley Eddington",
                "In chapters 1–11, Eddington explains, in non-technical and sometimes entertaining language, the main ideas of modern physics as it stood in 1927. " +
                        "In chapters 12–15, he then discuses what he considers their main philosophical and religious implications.",
                "Science/Philosophy","https://archive.org/download/natureofphysical00eddi/natureofphysical00eddi.pdf",
                "https://ia600200.us.archive.org/BookReader/BookReaderImages.php?zip=/3/items/natureofphysical00eddi/natureofphysical00eddi_jp2.zip&file=natureofphysical00eddi_jp2/natureofphysical00eddi_0007.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(6,"Our surroundings; an elementary general science","Clement, Arthur G. Collister, Morton Christian, Thurston, E. L. (Ernest Lawton)",
                "The immediate aim of Our Surroundings is to place the pupil in tune with the common things about him, giving him an understanding and appreciation of, and an interest in, his environment."
                        + "Our Surroundings draws upon many sciences to bring about this contact and understanding. With the help of these sciences it " +
                        "opens the door through which the pupil sees the true importance of the familiar and common things about him. It does more. It fires the pupil's interest, " +
                        "awakening the desire to go farther afield and to explore in more detail the territory of some particular science. It gives the preliminary view and contact that make possible " +
                        "an intelligent choice of the sciences for more extensive and intensive study in later school years.",
                "Science","https://archive.org/download/oursurroundingse00clemrich/oursurroundingse00clemrich.pdf",
                "https://ia802605.us.archive.org/BookReader/BookReaderImages.php?zip=/4/items/oursurroundingse00clemrich/oursurroundingse00clemrich_jp2.zip&file=oursurroundingse00clemrich_jp2/oursurroundingse00clemrich_0001.jp2&scale=4&rotate=0");
        book.setBookPrice(10);
        allBooks.add(book);
        book = new Book(7,"Science, religion and reality","Joseph Needham, Arthur James Balfour",
                "Essays on science, religion, and reality by various authors. Contents: Introduction; Magic Science and Religion; Historical relations of religion and science; Science and religion in the nineteenth century, The domain of physical science; Mechanistic biology and the religious consciousness; " +
                        "Sphere of religion; Religion and psychology; Science Christianity and Modern Civilization",
                "Science/Religion","https://archive.org/download/sciencereligionr00need/sciencereligionr00need.pdf",
                "https://ia800303.us.archive.org/BookReader/" +
                        "BookReaderImages.php?zip=/9/items/sciencereligionr00need/sciencereligionr00need_jp2.zip&file=sciencereligionr00need_jp2/sciencereligionr00need_0007.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(8,"Science fiction and the prediction of the future : essays on foresight and fallacy","Gary Westfahl, Wong Kin Yuen, Amy Kit-sze Chan",
                "Science fiction has always intrigued readers with depictions of an unforeseen future. Can the genre actually provide audiences with a glance into the world of tomorrow? " +
                        "This collection of fifteen international and interdisciplinary essays examines the genre's predictions and breaks new ground by considering the prophetic functions of science fiction films," +
                        " as well as science fiction literature.",
                "Science/Fiction","https://archive.org/download/" +
                "Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy/Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy_by_Gary_Westfahl_and_Donald_E._Palumbo.pdf",
                "https://ia801305.us.archive.org/BookReader/BookReaderImages.php?zip=/7/items/Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy/" +
                        "Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy_by_Gary_Westfahl_and_Donald_E._Palumbo_jp2.zip&file=Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy_by_Gary_Westfahl_and_Donald_E._Palumbo_jp2/Science_Fiction_and_the_Prediction_of_the_Future_Essays_on_Foresight_and_Fallacy_by_Gary_Westfahl_and_Donald_E._Palumbo_0000.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(9,"The survivor","Tom Cain",
                "The novel opens with Samuel Carver masquerading as a maintenance man. He sabotages the executive jet of wealthy Texan businessman Waylon McCabe. " +
                        "The sabotage fails and McCabe begins to suspect that he was the target of an assassination as opposed to a victim of a freak accident. The novel then jumps forward to continue" +
                        " the story of Cain's first novel, The Accident Man. Carver is recovering in a Swiss hospital and attempting to regain memories lost during the torture by that book's villain.The story centres around McCabe's attempt to obtain a lost Russian suitcase nuke in an effort to instigate a nuclear holocaust that would bring about the rapture; Carver aims to stop him.",
                "Nuclear terrorism","https://archive.org/download/survivor00corg/survivor00corg.pdf",
                "https://ia801601.us.archive.org/BookReader/BookReaderImages.php?zip=/21/items/survivor00corg/survivor00corg_jp2.zip&file=survivor00corg_jp2/survivor00corg_0001.jp2&scale=4&rotate=0");

        book.setBookPrice(10);
        allBooks.add(book);

        book = new Book(10,"Dracula","Bram Stoker",
                "Dracula is an 1897 Gothic horror novel by Irish author Bram Stoker. It introduced the character of Count Dracula and established many conventions of subsequent vampire fantasy. The novel tells the story of Dracula's " +
                        "attempt to move from Transylvania to England so that he may find new blood and spread the undead curse, and of the battle between Dracula and a small group of people led by Professor Abraham Van Helsing. ",
                "Horror/Gothic","https://archive.org/download/draculabr00stokuoft/draculabr00stokuoft.pdf",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Dracula1st.jpeg/200px-Dracula1st.jpeg");

        book.setBookPrice(10);
        allBooks.add(book);
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

        String appUserNoOfBooks = "0";
        if(appUser!=null)
        {
            numberOfBooksTextView.setText(String.valueOf(appUser.getAppUserNoOfBooks()));
        }

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
