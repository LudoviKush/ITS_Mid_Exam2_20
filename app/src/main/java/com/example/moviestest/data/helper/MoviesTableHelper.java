package com.example.moviestest.data.helper;

import android.provider.BaseColumns;

public class MoviesTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "movie";
    public static final String IMG_POSTER = "img_poster";
    public static final String IMG_DESCRIPTION = "img_description";
    public static final String TITLE = "title";
    public static final String FILM_ID = "film_id";
    public static final String DESCRIPTION = "description";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FILM_ID + " INTEGER, " +
            TITLE + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            IMG_POSTER + " BLOB, " +
            IMG_DESCRIPTION + " TEXT );";



}