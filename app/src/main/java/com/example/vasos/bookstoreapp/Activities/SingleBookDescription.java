package com.example.vasos.bookstoreapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;

public class SingleBookDescription extends Activity {
    private Button readButton;
    private Button backToAllBooksButton;
    private Button addToMyBooksButton;
    public String bookToReadFileTitle;
    private Toolbar actionBarToolbar;
    private Context context;
    private LinearLayout downloadProgresBar;
    private TextView bookTitleDescriptionTextView,bookDescriptionTextTextView,bookpriceTextView;
    private ImageView bookDescriptionImageView;
    private static boolean isAddedToBooks;
    private String selectedBookUrl = "";
    private static String TestselectedBookName = "";
    boolean isMyBooks = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_single_book_description);
        context = this;
        setViews();
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");
        String id = intent.getStringExtra("Id");
        String imageUrl = intent.getStringExtra("ImageUrl");
        isMyBooks = intent.getExtras().getBoolean("IsMyBooks");
        selectedBookUrl = intent.getStringExtra("BookUrl");

        if(context!=null)
        Glide.with(context).load(imageUrl).fitCenter().into(bookDescriptionImageView);
        bookToReadFileTitle = title;
        bookTitleDescriptionTextView.setText(bookToReadFileTitle);
        bookDescriptionTextTextView.setText(description);
        bookpriceTextView.setText(intent.getStringExtra("BookPrice") + " €");

        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent backToAllBooks = new Intent(SingleBookDescription.this,AllBooks.class);
                if(isMyBooks)
                {
                    backToAllBooks.putExtra("MyBooksIntent",true);
                }
                startActivity(backToAllBooks);
            }
        });

        if(appUser.getUserBooksBought().size()>0)
            for(int i = 0;i<=appUser.getUserBooksBought().size()-1;i++)
            {
                if(appUser.getUserBooksBought().get(i).getBookTitle().equals(bookToReadFileTitle))
                {
                    readButton.setVisibility(View.VISIBLE);
                    addToMyBooksButton.setVisibility(View.GONE);
                }
            }
    }

    private void setViews()
    {
        bookDescriptionImageView = (ImageView) findViewById(R.id.bookDescriptionImageView);
        downloadProgresBar = (LinearLayout) findViewById(R.id.downloadProgresBar);
        readButton = (Button) findViewById(R.id.readButton);
        backToAllBooksButton = (Button) findViewById(R.id.backToAllBooksButton);
        addToMyBooksButton = (Button) findViewById(R.id.addToMyBooksButton);
        actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_description_view);
        bookTitleDescriptionTextView = (TextView) findViewById(R.id.bookTitleDescriptionTextView);
        bookDescriptionTextTextView = (TextView) findViewById(R.id.bookDescriptionTextTextView);
        bookpriceTextView = (TextView) findViewById(R.id.bookpriceTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readBook = new Intent(SingleBookDescription.this,BookView.class);
                readBook.putExtra("Title",bookToReadFileTitle);
                startActivity(readBook);
            }
        });
        backToAllBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
                if(isMyBooks)
                {
                    goBack.putExtra("MyBooksIntent",true);
                }
                goBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goBack);
            }
        });
        addToMyBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SingleBookDescription.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SingleBookDescription.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            101);
                    }
                    else
                {
                    Intent buyBook = new Intent(SingleBookDescription.this,BuyBookActivity.class);
                    buyBook.putExtra("Title",bookToReadFileTitle);
                    buyBook.putExtra("Url",selectedBookUrl);
                    startActivity(buyBook);

                   // download(selectedBookUrl);
                }
                }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
        if(isMyBooks)
        {
            goBack.putExtra("MyBooksIntent",true);
        }
        goBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goBack);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(SingleBookDescription.this,AllBooks.class);
                if(isMyBooks)
                {
                    goBack.putExtra("MyBooksIntent",true);
                }
                startActivity(goBack);
                break;
        }
        return true;
    }

    public void download(String selectedBookUrl)
    {
        new DownloadFile().execute(selectedBookUrl, bookToReadFileTitle+".pdf");
    }

     class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "YourBooks");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader fileDownloader = new FileDownloader();
            fileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

      class FileDownloader {
        private static final int  MEGABYTE = 1024 * 1024;

        public  void downloadFile(String fileUrl, File directory){
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          downloadProgresBar.setVisibility(View.VISIBLE);
                                      }
                                  });

                }
                fileOutputStream.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayFileDownloaded();
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         private  void displayFileDownloaded() {
             downloadProgresBar.setVisibility(View.GONE);
             addToMyBooksButton.setVisibility(View.GONE);//!!
             readButton.setVisibility(View.VISIBLE);//!!!!!
             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setMessage("Successfully added to your books!")
                     .setCancelable(false)
                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             //do things
                             dialog.dismiss();
                         }
                     });
             AlertDialog alert = builder.create();
             alert.show();

        }
     }
}
