package com.example.vasos.bookstoreapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.vasos.bookstoreapp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class BookView extends Activity {

    private PDFView pdfView;
    public static String bookFileTitle = "ProblemSolvingwithAlgorithmsandDataStructures_compressed.pdf";
    public static int currentPage = 0;
    private Handler handler;
    private boolean viewedFirstPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_view);

        pdfView = (PDFView) findViewById(R.id.pdfView);


        Toast toast = Toast.makeText(getBaseContext(), bookFileTitle, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        pdfView.fromAsset(bookFileTitle)
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
                        page = page + 1;
                        currentPage = page;
                        if(viewedFirstPage)
                        {
                            Toast.makeText(getBaseContext(), String.valueOf(currentPage), Toast.LENGTH_SHORT).show();
                        }
                        viewedFirstPage= true;
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
                .load();
    }

   /* private static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }*/

}
