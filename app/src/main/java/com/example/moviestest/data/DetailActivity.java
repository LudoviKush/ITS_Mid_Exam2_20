package com.example.moviestest.data;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviestest.R;
import com.example.moviestest.data.helper.MoviesProvider;
import com.example.moviestest.data.helper.MoviesTableHelper;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "ASDA";
    String mId;
    TextView mTitle, mDescription;
    ImageView mImage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail Activity");

        mTitle = findViewById(R.id.textViewTitle);
        mDescription = findViewById(R.id.textViewDescription);
        mImage = findViewById(R.id.imageViewPoster);

        Intent intentStartedActivity = getIntent();
        if(intentStartedActivity.hasExtra("original_title")){
            String title = getIntent().getExtras().getString("original_title");
            String poster = getIntent().getExtras().getString("poster_path");
            String overview = getIntent().getExtras().getString("overview");


            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mImage);

            mTitle.setText(title);
            mDescription.setText(overview);
        } else {
            Toast.makeText(this,"No API data", Toast.LENGTH_SHORT).show();
        }
    }


    }

