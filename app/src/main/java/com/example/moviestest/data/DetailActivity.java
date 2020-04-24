package com.example.moviestest.data;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.moviestest.R;
import com.example.moviestest.data.db.MoviesProvider;
import com.example.moviestest.data.db.MoviesTableHelper;

public class DetailActivity extends AppCompatActivity {

    String mId;
    TextView mTitle, mDescription;
    ImageView mImage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail Activity");


        if(getIntent().getExtras() != null){
            mId=getIntent().getExtras().getString("position");
        }

        mTitle = findViewById(R.id.textViewTitle);
        mDescription = findViewById(R.id.textViewDescription);
        mImage = findViewById(R.id.imageViewPoster);
            Cursor vCursor =getContentResolver().query(MoviesProvider.MOVIES_URI, new String[]{MoviesTableHelper.TITLE, MoviesTableHelper.DESCRIPTION, MoviesTableHelper.IMG_POSTER}, MoviesTableHelper._ID + " = "+ mId, null, null);

            if(vCursor.moveToNext()) {



                mTitle.setText(vCursor.getString(vCursor.getColumnIndex(MoviesTableHelper.TITLE)));
                mDescription.setText(getText(vCursor.getColumnIndex(MoviesTableHelper.DESCRIPTION)));
                Glide.with(DetailActivity.this).load(vCursor.getString(vCursor.getColumnIndex(MoviesTableHelper.IMG_POSTER))).into(mImage);
            }

        }

    }

