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

    int mId;
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

        if(getIntent().getExtras() != null)
        {

            mId = getIntent().getExtras().getInt("_ID");
            Cursor vCursor = getContentResolver().query(Uri.parse(MoviesProvider.MOVIES_URI + "/" + mId), null, null, null);
            vCursor.moveToNext();
            String vTitle = vCursor.getString(vCursor.getColumnIndex(MoviesTableHelper.TITLE));
            String vDescription = vCursor.getString(vCursor.getColumnIndex(MoviesTableHelper.DESCRIPTION));
            String vImage = "https://image.tmdb.org/t/p/w500/" + vCursor.getString(vCursor.getColumnIndex(MoviesTableHelper.IMG_POSTER));

            mTitle.setText(vTitle);
            mDescription.setText(vDescription);
            Glide.with(DetailActivity.this).load(vImage).into(mImage);


        }

    }
}
