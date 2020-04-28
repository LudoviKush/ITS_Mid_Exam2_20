package com.example.moviestest.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.moviestest.R;
import com.example.moviestest.services.MainResponse;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private Context context;
    private ArrayList<MainResponse.ResultsFilm> mFilm;
    private OnFilmClicked mOnFilmClicked;
    private String TAG = "ASDA";

    public Adapter(Context context, ArrayList<MainResponse.ResultsFilm> mFilm, OnFilmClicked onFilmListener) {
        this.context = context;
        this.mFilm = mFilm;
        this.mOnFilmClicked = onFilmListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_film, parent, false);
        return new ViewHolder(view, new OnFilmListener() {
            @Override
            public void onFilmClick( int position ) {

                mOnFilmClicked.onFilmId(mFilm.get(position).getId());
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = "https://image.tmdb.org/t/p/w500/" + mFilm.get(position).getPoster_path();

        holder.titleFilm.setText(mFilm.get(position).getTitle());
        Glide.with(context)
                .load(image)
                .centerCrop()
                .into(holder.poster);
    }


    @Override
    public int getItemCount() {
        return mFilm.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        TextView titleFilm;
        OnFilmListener onFilmListener;

        public ViewHolder(@NonNull View itemView, OnFilmListener onFilmListener) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            titleFilm = itemView.findViewById(R.id.filmTitle);


          this.onFilmListener = onFilmListener;
          itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onFilmListener.onFilmClick(getAdapterPosition());
            Log.d(TAG, "onClicked: ");
        }


    }

    public interface OnFilmListener{
        void onFilmClick(int position);
    }
    public interface OnFilmClicked{
        void onFilmId(long id);
    }




}