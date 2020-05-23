package com.example.vasos.bookstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vasos.bookstoreapp.Activities.SingleBookDescription;
import com.example.vasos.bookstoreapp.Models.Book;
import com.example.vasos.bookstoreapp.R;

import java.util.ArrayList;

public class BooksListViewAdapter  extends ArrayAdapter<Book> {

    Context context;
    ArrayList<Book> books = new ArrayList<>();


    public BooksListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Book> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.books = objects;
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
        viewHolder.singleBookImageView = (ImageView) view.findViewById(R.id.singleBookImageView);
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
        viewHolder.bookCategoryTV.setText(book.getBookGenre());
        viewHolder.bookAuthorTV.setText(book.getBookAuthor());
        viewHolder.bookISBNTV.setText(book.getBookDescription());
        Glide.with(getContext()).load(book.getBookImageUrl()).fitCenter().into(viewHolder.singleBookImageView);



        return view;
    }


    private static class ViewHolder {

        TextView bookTitleTV;
        TextView bookAuthorTV;
        TextView bookCategoryTV;
        TextView bookISBNTV;
        TextView idTV;
        ImageView singleBookImageView;
        private Button viewBookButton;

    }
}
