package com.example.vasos.bookstoreapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.vasos.bookstoreapp.Models.AppUser;
import com.example.vasos.bookstoreapp.Models.Book;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db";
    private static final String USERS_TABLE_NAME = "userstable";
    private static final String BOOKS_TABLE_NAME = "boookstable";

    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_USER_ID = "userid";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_NUMBER_OF_BOOKS = "usernumberofbooks";

    private static final String COLUMN_BOOK_ID = "bookid";
    private static final String COLUMN_BOOK_TITLE = "booktitle";
    private static final String COLUMN_BOOK_URL = "bookurl";
    private static final String COLUMN_BOOK_DESCRIPTION = "bookdescription";


    private static final Book Book = null;
    SQLiteDatabase sqLiteDatabase;
    Book [] books = new Book[1];



    public SQLiteDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        createTables(sqLiteDatabase);
        createBooks();
        insertBooks(sqLiteDatabase,books);


    }

    private void createBooks() {
        books[0] = new Book(0,"Benjamin Fraklin Bio", "Benjamin Fraklin","Written initially to guide his son, Franklin's autobiography is a lively, spellbinding account of his unique and eventful life. Stylistically his best work, it has become a classic in world literature, " +
            "one to inspire and delight readers everywhere. ","Biography","https://archive.org/download/autobiobenfran00miffrich/autobiobenfran00miffrich.pdf","");
    }

    private void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+USERS_TABLE_NAME+" ( " + COLUMN_USER_ID + " INTEGER PRIMARY KEY, "+COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_USER_NUMBER_OF_BOOKS + " INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE "+BOOKS_TABLE_NAME+" ( " + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY, "+COLUMN_BOOK_TITLE + " TEXT, " + COLUMN_BOOK_URL + " TEXT, " + COLUMN_BOOK_DESCRIPTION + " TEXT)");
    }

    private void insertBooks(SQLiteDatabase sqLiteDatabase, Book[] books)
    {
        ContentValues values = new ContentValues();

        for(int i = 0; i < books.length; i ++)
        {
            values.put(COLUMN_BOOK_ID, books[i].getBookId());
            values.put(COLUMN_BOOK_TITLE, books[i].getBookTitle());
            values.put(COLUMN_BOOK_URL, books[i].getBookTitle());
            values.put(COLUMN_BOOK_DESCRIPTION, books[i].getBookDescription());
        }

        //SQLiteDatabase db = this.getWritableDatabase();

        sqLiteDatabase.insert(BOOKS_TABLE_NAME, null, values);


    }

    public void insertUser(AppUser appUser)
    {
        ContentValues values = new ContentValues();

            values.put(COLUMN_USER_ID, appUser.getAppUserId());
            values.put(COLUMN_USERNAME, appUser.getAppUserName());
            values.put(COLUMN_PASSWORD, appUser.getAppUserPassword());
            values.put(COLUMN_USER_NUMBER_OF_BOOKS, appUser.getAppUserNoOfBooks());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(BOOKS_TABLE_NAME, null, values);
        db.close();

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
