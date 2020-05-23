package com.example.vasos.bookstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.vasos.bookstoreapp.Activities.SingleBookDescription;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.util.ArrayList;
import java.util.List;

public class BooksListViewAdapter  extends ArrayAdapter<Book> {

    Context context;
    ArrayList<Book> books = new ArrayList<>() ;


    public BooksListViewAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.books = books;
    }


    @Override
    public int getCount() {
        return books.size();
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

        Book book = (Book) getItem(i);
        viewHolder.idTV.setText(String.valueOf(book.getBookId()));
        viewHolder.bookTitleTV.setText(book.getBookTitle()) ;
        viewHolder.bookCategoryTV.setText(book.getBookTitle());
        viewHolder.bookAuthorTV.setText(book.getBookTitle());
        viewHolder.bookISBNTV.setText(book.getBookTitle());



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
