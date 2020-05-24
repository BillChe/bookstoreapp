package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vasos.bookstoreapp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class BookView extends Activity {

    private PDFView pdfView;
    public static int currentPage = 0;
    private Handler handler;
    private boolean viewedFirstPage;
    public static String lastOpenedBookName = "";
    private String TestselectedBookName = "";
    private ProgressBar progressBar;
    private  int lastPage = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_view);
        Intent intent = getIntent();
        TestselectedBookName = intent.getStringExtra("Title");
        lastOpenedBookName = intent.getStringExtra("lastOpenedBookName");
        if(lastOpenedBookName!=null)
        {
            if(!lastOpenedBookName.equals(""))
            {
                TestselectedBookName = lastOpenedBookName;
            }
        }
        else
        {
            lastOpenedBookName = TestselectedBookName;
        }

        pdfView = (PDFView) findViewById(R.id.pdfView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarPdf);

        pdfView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Toast toast = Toast.makeText(getBaseContext(), TestselectedBookName, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();

        //download(pdfView,TestselectedBookUrl);
        view(pdfView);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

   public void download(View v, String selectedBookUrl)
   {
      // new DownloadFile().execute(selectedBookUrl, TestselectedBookName);
   }

    public void view(View v)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/YourBooks/" + TestselectedBookName+".pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        pdfView.fromFile(pdfFile)
                //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {

                        return false;
                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                            Toast.makeText(getBaseContext(), String.valueOf(page + 1), Toast.LENGTH_SHORT).show();
                    }
                })
                // allows to draw something on the current page, usually visible in the middle of the screen
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pdfView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .load();

    }


}
