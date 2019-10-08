package com.example.vasos.bookstoreapp.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Activities.SingleBookDescription;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.util.ArrayList;
public class BooksListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Book> books ;


    public BooksListViewAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        final View result;



            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.singlebookitem, null);
            viewHolder.idTV = (TextView) view.findViewById(R.id.idTV);
            viewHolder.bookTitleTV = (TextView) view.findViewById(R.id.booktitleTV);
            viewHolder.bookCategoryTV = (TextView) view.findViewById(R.id.bookCategoryTV);
            viewHolder.bookAuthorTV = (TextView) view.findViewById(R.id.bookAuthorTV);
            viewHolder.bookISBNTV = (TextView) view.findViewById(R.id.bookISBNTV);
            viewHolder.viewBookButton = (Button) view.findViewById(R.id.viewBookButton);
            viewHolder.viewBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent viewBook = new Intent(context,SingleBookDescription.class);
                    context.startActivity(viewBook);
                }
            });


            viewHolder.idTV.setText(String.valueOf(books.get(i).getBookId()));
            viewHolder.bookTitleTV.setText(books.get(i).getBookTitle()) ;
            viewHolder.bookCategoryTV.setText(books.get(i).getBookTitle());
            viewHolder.bookAuthorTV.setText(books.get(i).getBookTitle());
            viewHolder.bookISBNTV.setText(books.get(i).getBookTitle());


            //view.setTag(viewHolder);



        return view;
    }


    private static class ViewHolder {

        TextView bookTitleTV;
        TextView bookAuthorTV;
        TextView bookCategoryTV;
        TextView bookISBNTV;
        TextView idTV;
        private Button viewBookButton;

    }
}
