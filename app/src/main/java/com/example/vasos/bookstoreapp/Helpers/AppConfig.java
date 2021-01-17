package com.example.vasos.bookstoreapp.Helpers;

public class AppConfig {
    // Server user login url
    //http://users.iee.ihu.gr/~it093504/boostore_api/login.php?email=maniakas.nikoss@gmail.com&password=darklord
    public static String URL_LOGIN = "https://users.iee.ihu.gr/~it093504/boostore_api/login.php";
    // Server user register url
    //http://users.iee.ihu.gr/~it093504/boostore_api/signup.php?email=maniakas.nikoss@gmail.com&password=darklord&name=Nikos
    //example: http://users.iee.ihu.gr/~it093504/boostore_api/signup.php?email=maniakas.nikoss@gmail.com&password=darklord&name=billbill&username=billbill3
    public static String URL_REGISTER = "https://users.iee.ihu.gr/~it093504/boostore_api/signup.php";
    // search book url
    public static String URL_SEARCH_BOOK_INFO = "https://users.iee.ihu.gr/~it093504/boostore_api/search.php";
    // Server book buy url
    //example: https://users.iee.ihu.gr/~it093504/boostore_api/buybook.php?user_id=1&book_id=11
    public static String URL_BOOK_BUY_INFO = "https://users.iee.ihu.gr/~it093504/boostore_api/buybook.php?user_id=";
     // Server book info url
    //public static String URL_BOOK_INFO = "http://users.iee.ihu.gr/~it093504//api/getBookInfo.php";
    //get buyed books for user
    //example: http://users.iee.ihu.gr/~it093504/boostore_api/getBooksBuyed.php?user_id=1
    public static String URL_BOOKS_BUYED = "https://users.iee.ihu.gr/~it093504/boostore_api/getBooksBuyed.php?user_id=";
}
