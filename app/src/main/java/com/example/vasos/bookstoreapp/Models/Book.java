package com.example.vasos.bookstoreapp.Models;

public class Book
{
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    private String bookGenre;
    private String bookUrl;
    private String bookImageUrl;
    private double bookPrice;


    public Book(int bookId, String bookTitle, String bookAuthor, String bookDescription, String bookGenre, String bookUrl, String bookImageUrl) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.bookGenre = bookGenre;
        this.bookUrl = bookUrl;
        this.bookImageUrl = bookImageUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookImageUrl()
    {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl)
    {
        this.bookImageUrl = bookImageUrl;
    }

    public double getBookPrice()
    {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice)
    {
        this.bookPrice = bookPrice;
    }
}
