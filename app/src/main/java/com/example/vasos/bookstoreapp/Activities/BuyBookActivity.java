package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.vasos.bookstoreapp.Activities.MainActivity.allBooks;
import static com.example.vasos.bookstoreapp.Activities.MainActivity.appUser;


public class BuyBookActivity extends Activity {
    ImageView bookPayImageView2;
    Button submitButton;
    EditText cardNumberEditText,c2cEditText;
    TextView cardNumberSumTextView;
    Spinner cardMonthSpinner, cardYearSpinner;
    private LinearLayout downloadProgresBar;
    String bookUrl,bookToReadFileTitle = "";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buy_book);
        context = this;
        if(getIntent().getExtras()!=null)
            bookUrl = getIntent().getExtras().getString("Url");
            bookToReadFileTitle= getIntent().getExtras().getString("Title");

        setViews();
        setListeners();


    }

    private void setViews()
    {
        downloadProgresBar = (LinearLayout)  findViewById(R.id.downloadProgresBar);
        cardNumberEditText = (EditText)  findViewById(R.id.cardNumberEditText);
        c2cEditText = (EditText)  findViewById(R.id.c2cEditText);
        cardNumberSumTextView = (TextView)  findViewById(R.id.cardNumberSumTextView);
        bookPayImageView2 = (ImageView) findViewById(R.id.bookPayImageView2);
        cardMonthSpinner = (Spinner) findViewById(R.id.cardMonthSpinner);
        cardYearSpinner = (Spinner) findViewById(R.id.cardYearSpinner);
        try
        {
            Glide.with(getApplicationContext()).load("https://img.pngio.com/credit-card-icons-png-download-visa-mastercard-discover-logo-credit-card-logos-png-840_213.png").fitCenter().into(bookPayImageView2);
        }
        catch (Exception e)
        {

        }
        submitButton = (Button) findViewById(R.id.submitButton);

    }

    private void setListeners()
    {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String s = cardNumberEditText.getText().toString();
                String s2 = c2cEditText.getText().toString();

                if(!s.isEmpty()&&s.length()==16&&!s2.isEmpty()&&s2.length()==3)
                {
                    download(bookUrl);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please fill your credit card details")
                            .setCancelable(true)
                            .setTitle("Invalid Card Details")
                            .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info))
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
        });

        cardNumberEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent)
            {
                String s = cardNumberEditText.getText().toString();

                if(!s.isEmpty()&&s.length()>=0)
                {
                    cardNumberSumTextView.setText(String.valueOf(s.length()));
                }
                else
                {
                    cardNumberSumTextView.setText("0");
                }

                return false;
            }
        });

        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7","8", "9","10", "11", "12"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardMonthSpinner.setAdapter(adapter);


        String[] arrayYearsSpinner = new String[] {
                "20", "21", "22", "23", "24", "25", "26"
        };

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayYearsSpinner);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardYearSpinner.setAdapter(adapterYear);

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

    class FileDownloader
    {
        private static final int  MEGABYTE = 1024 * 1024;

        public  void downloadFile(String fileUrl, File directory){
            try
            {
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

        private  void displayFileDownloaded()
        {
            downloadProgresBar.setVisibility(View.GONE);
            appUser.setAppUserNoOfBooks(appUser.getAppUserNoOfBooks()+1);
            ArrayList<Book> books = new ArrayList<>();
            for(int i = 0;i<=allBooks.size()-1;i++)
            {
                if(allBooks.get(i).getBookTitle().equals(bookToReadFileTitle))
                {
                    appUser.getUserBooksBought().add(allBooks.get(i));
                }
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Successfully saved to your books!")
                    .setMessage("Your book is in \"YourBooks\" folder")
                    .setCancelable(false)
                    .setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            dialog.dismiss();
                            Intent buyBook = new Intent(BuyBookActivity.this,AllBooks.class);
                            buyBook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(buyBook);
                        }
                    });


            AlertDialog alert = builder.create();
            alert.show();

        }
    }
}
