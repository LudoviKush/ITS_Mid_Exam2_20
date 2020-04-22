package com.example.moviestest.data.db;


import android.provider.BaseColumns;

public class MoviesTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "movie";
    public static final String PAGE = "page";
    public static final String IMG_POSTER = "img_poster";
    public static final String IMG_DESCRIPTION = "img_description";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT, " +
            PAGE + " INTEGER, " +
            DESCRIPTION + " TEXT, " +
            IMG_POSTER + " TEXT, " +
            IMG_DESCRIPTION + " TEXT ) ;";


}


