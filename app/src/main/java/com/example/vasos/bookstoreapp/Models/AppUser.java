package com.example.vasos.bookstoreapp.Models;

import java.util.ArrayList;

public class AppUser
{
    private int appUserId;
    private String appUserName;
    private String appUserPassword;
    public int appUserNoOfBooks;
    public boolean isUserLoggedIn;
    private ArrayList<Book> userBooksBought = new ArrayList<>();

    public AppUser(int appUserId, String appUserName, String appUserPassword, int appUserNoOfBooks, boolean isUserLoggedIn ) {
        this.appUserId = appUserId;
        this.appUserName = appUserName;
        this.appUserPassword = appUserPassword;
        this.appUserNoOfBooks = appUserNoOfBooks;
        this.isUserLoggedIn = isUserLoggedIn;
    }

    public int getAppUserNoOfBooks() {
        return appUserNoOfBooks;
    }

    public void setAppUserNoOfBooks(int appUserNoOfBooks) {
        this.appUserNoOfBooks = appUserNoOfBooks;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public boolean isUserLoggedIn()
    {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn)
    {
        isUserLoggedIn = userLoggedIn;
    }

    public ArrayList<Book> getUserBooksBought()
    {
        return userBooksBought;
    }

    public void setUserBooksBought(ArrayList<Book> userBooksBought)
    {
        this.userBooksBought = userBooksBought;
    }
}

