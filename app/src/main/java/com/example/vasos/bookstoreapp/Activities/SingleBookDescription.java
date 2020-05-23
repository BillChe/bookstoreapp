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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SingleBookDescription extends Activity {
    private Button readButton;
    private Button backToAllBooksButton;
    private Button addToMyBooksButton;
    public static String bookToReadFileTitle;
    private Toolbar actionBarToolbar;
    private Context context;
    private LinearLayout downloadProgresBar;
    private TextView bookTitleDescriptionTextView,bookDescriptionTextTextView;
    private static boolean isAddedToBooks;
    private static String TestselectedBookUrl = "https://archive.org/download/guidetohistoryof00sart/guidetohistoryof00sart.pdf";
    private static String TestselectedBookName = "benfran.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_single_book_description);
        context = this;

        bookToReadFileTitle = BookView.bookFileTitle;
        downloadProgresBar = (LinearLayout) findViewById(R.id.downloadProgresBar);
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
                if (ContextCompat.checkSelfPermission(SingleBookDescription.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SingleBookDescription.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            101);

                    }
                    else
                {
                    download(TestselectedBookUrl);

                }
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

    public void download(String selectedBookUrl)
    {
        new DownloadFile().execute(selectedBookUrl, TestselectedBookName);
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
     }
}
