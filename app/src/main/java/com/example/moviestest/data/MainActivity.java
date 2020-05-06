package com.example.moviestest.data;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.moviestest.R;
import com.example.moviestest.data.helper.MoviesDB;
import com.example.moviestest.data.helper.MoviesProvider;
import com.example.moviestest.data.helper.MoviesTableHelper;
import com.example.moviestest.data.helper.Utils;
import com.example.moviestest.services.MainResponse;
import com.example.moviestest.services.WebService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements com.example.moviestest.data.Adapter.OnFilmClicked   {

    private static final String TAG ="ASDA";
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "675236176baaaafd2ea29287a326d89b";
    public static String CATEGORY = "popular";
    public static String LANGUAGE = "it";



    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<MainResponse.Movie> listOfMovie;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.listFilm);
        getHelpButton = findViewById(R.id.buttonGetHelp);

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.themoviedb.org/faq/general"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


       if(Utils.isNetworkAvailable(getApplicationContext())) {
           getFeed();
       } else {

            mostraLista();
           Toast.makeText(this, "Controlla la tua connessione e riprova", Toast.LENGTH_LONG).show();
       }
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You are leaving the app, are you sure about this?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes, close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i){
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveIntoDB() {


        for(int i=0; i<listOfMovie.size();i++){
            addMovies(listOfMovie.get(i));
        }

    }
    @Override
    public void onFilmId(long id) {

    }


    public void getFeed(){


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
                listOfMovie = (ArrayList<MainResponse.Movie>) results.getResults();
                saveIntoDB();
                adapter = new Adapter(MainActivity.this, (ArrayList<MainResponse.Movie>) listOfMovie, MainActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.d(TAG, "an error occurred");
            }
        });
    }
    public void addMovies(MainResponse.Movie movie) {

        ContentValues values = new ContentValues();

        values.put(MoviesTableHelper.TITLE, movie.getTitle());
        values.put(MoviesTableHelper.FILM_ID, movie.getId());
        values.put(MoviesTableHelper.DESCRIPTION, movie.getOverview());
        values.put(MoviesTableHelper.IMG_POSTER, movie.getPoster_path());
        values.put(MoviesTableHelper.IMG_DESCRIPTION, movie.getBackdrop_path());

        this.getContentResolver().insert(MoviesProvider.MOVIES_URI, values);

    }
    public void mostraLista() {

        listOfMovie = new ArrayList<>();

        Cursor vCursor = getContentResolver().query(MoviesProvider.MOVIES_URI, null, null, null, null);

        while (vCursor.moveToNext()) {

            int vTitoloColumnIndex = vCursor.getColumnIndex(MoviesTableHelper.TITLE);
            int vFilmIdColumnIndex = vCursor.getColumnIndex(MoviesTableHelper.FILM_ID);
            int vDescColumnIndex = vCursor.getColumnIndex(MoviesTableHelper.DESCRIPTION);
            int vImagePostColumnIndex = vCursor.getColumnIndex(MoviesTableHelper.IMG_POSTER);
            int vImageDescColumnIndex = vCursor.getColumnIndex(MoviesTableHelper.IMG_DESCRIPTION);

            int vId = vCursor.getInt(vFilmIdColumnIndex);
            String vTitolo = vCursor.getString(vTitoloColumnIndex);
            String vDesc = vCursor.getString(vDescColumnIndex);
            String vImagePost = vCursor.getString(vImagePostColumnIndex);
            String vImageDesc = vCursor.getString(vImageDescColumnIndex);

            listOfMovie.add(new MainResponse.Movie(vId, vTitolo, vDesc, vImagePost, vImageDesc));

        }

        adapter = new Adapter(MainActivity.this, listOfMovie, MainActivity.this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        adapter.notifyDataSetChanged();

        vCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    adapter.setSearching(true);
                }
                else{
                    adapter.setSearching(false);
                    adapter.notifyDataSetChanged();
                    }
                }
            }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }


            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


                return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}