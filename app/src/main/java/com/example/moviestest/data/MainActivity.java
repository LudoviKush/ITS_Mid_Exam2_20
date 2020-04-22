package com.example.moviestest.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.widget.Toast;
import com.example.moviestest.R;
import com.example.moviestest.services.MainResponse;
import com.example.moviestest.services.WebService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements com.example.moviestest.data.Adapter.OnFilmListener {

    private static final String TAG ="ASDA";
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "675236176baaaafd2ea29287a326d89b";
    public static String CATEGORY = "popular";
    public static String LANGUAGE = "it";

    Adapter Adapter;
    RecyclerView recyclerView;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();
    List<MainResponse.ResultsBean> listOfMovie;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Movies");

        //isConnected();

        recyclerView = findViewById(R.id.listFilm);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService service = retrofit.create(WebService.class);
        Call<MainResponse> call = service.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                MainResponse results = response.body();
                listOfMovie = results.getResults();
                for (int i = 0; i < listOfMovie.size(); i++) {
                    String title = listOfMovie.get(i).getTitle();
                    String image = "https://image.tmdb.org/t/p/w500/" + listOfMovie.get(i).getPoster_path();
                    titles.add(title);
                    images.add(image);
                }
                Adapter = new Adapter(getApplicationContext(), titles, images, MainActivity.this::onFilmClick); // cosa strcacazzo devo metterci qui?
                recyclerView.setAdapter(Adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            }
            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });


    }

    //CLICK SULL' ELEMENTO PORTA A DETAIL ACTIVITY
    @Override
    public void onFilmClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);

    }


  /*  public boolean isConnected(){
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            Log.d(TAG, "isConnected: youggottit");
            Toast.makeText(context, "YEEEEES YOU ARE CONNECTED", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Log.d(TAG, "NOT CONNECTED");
            return false;
        }
    } */
}
