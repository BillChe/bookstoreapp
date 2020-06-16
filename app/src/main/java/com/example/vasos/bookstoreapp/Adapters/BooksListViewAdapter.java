package com.example.vasos.bookstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    boolean isMyBooks = false;

    public boolean isMyBooks()
    {
        return isMyBooks;
    }

    public void setMyBooks(boolean myBooks)
    {
        isMyBooks = myBooks;
    }



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

    @NonNull
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
        viewHolder.bookpriceTV = (TextView) view.findViewById(R.id.bookpriceTV);
        viewHolder.viewBookButton = (Button) view.findViewById(R.id.viewBookButton);
        viewHolder.singleBookImageView = (ImageView) view.findViewById(R.id.singleBookImageView);


        final Book book = (Book) getItem(i);
        Glide.with(getContext()).load(book.getBookImageUrl()).fitCenter().into(viewHolder.singleBookImageView);
        viewHolder.idTV.setText(String.valueOf(book.getBookId()));
        viewHolder.bookTitleTV.setText(book.getBookTitle()) ;
        viewHolder.bookCategoryTV.setText(book.getBookGenre());
        viewHolder.bookAuthorTV.setText(book.getBookAuthor());
        viewHolder.bookpriceTV.setText(String.valueOf(book.getBookPrice()+" €"));


        viewHolder.viewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleBookDescriptionIntent(book);

            }
        });
        viewHolder.singleBookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                singleBookDescriptionIntent(book);
            }
        });

        return view;
    }

    private void singleBookDescriptionIntent(Book book)
    {
        Intent viewBook = new Intent(context,SingleBookDescription.class);
        Bundle extras = new Bundle();
        extras.putString("Title",book.getBookTitle());
        extras.putString("Description",book.getBookDescription());
        extras.putString("Id", String.valueOf(book.getBookId()));
        extras.putString("ImageUrl",book.getBookImageUrl());
        extras.putString("BookUrl",book.getBookUrl());
        extras.putString("BookPrice",String.valueOf(book.getBookPrice()));
        extras.putBoolean("IsMyBooks",isMyBooks);
        viewBook.putExtras(extras);
        context.startActivity(viewBook);
    }


    private static class ViewHolder {
        TextView bookTitleTV;
        TextView bookAuthorTV;
        TextView bookCategoryTV;
        TextView bookpriceTV;
        TextView idTV;
        ImageView singleBookImageView;
        Button viewBookButton;
    }
}
