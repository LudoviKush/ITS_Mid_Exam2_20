package com.example.moviestest.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.moviestest.services.MainResponse.Movie;

import java.util.ArrayList;
import java.util.List;


public class MoviesDB extends SQLiteOpenHelper {

    private static final String TAG = MoviesDB.class.getSimpleName();
    public static final String DB_NAME = "movies.db";
    public static final int VERSION = 1;

    public MoviesDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(MoviesTableHelper.CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}