package com.example.moviestest.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviestest.services.MainResponse;

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
        try{
            db.execSQL(MoviesTableHelper.CREATE);
        } catch(SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(MoviesTableHelper.DROP_QUERY);
    this.onCreate(db);
    }

    public void addFlower(MainResponse mainResponse){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put();

    }
}