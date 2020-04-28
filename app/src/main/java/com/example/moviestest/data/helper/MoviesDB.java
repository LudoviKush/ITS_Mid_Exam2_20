package com.example.moviestest.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviestest.services.MainResponse;
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
        try {
            db.execSQL(MoviesTableHelper.CREATE);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MoviesTableHelper.DROP_QUERY);
        this.onCreate(db);
    }

    public void addMovies(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MoviesTableHelper.TITLE, movie.getTitle());
        values.put(MoviesTableHelper._ID, movie.getId());
        values.put(MoviesTableHelper.DESCRIPTION, movie.getOverview());
        values.put(MoviesTableHelper.IMG_POSTER, movie.getPoster_path());

        db.insert(MoviesTableHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<Movie> getFilms() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(MoviesTableHelper.GET_MOVIE_QUERY, null);
        List<Movie> MovieList = new ArrayList<>();

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.setTitle(cursor.getColumnName(cursor.getColumnIndex(MoviesTableHelper.TITLE)));

                    MovieList.add(movie);

                } while (cursor.moveToNext());
            }
        }
            return MovieList;

    }
}