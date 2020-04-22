package com.example.moviestest.data;

import android.content.Context;
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

import retrofit2.Callback;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private Context context;
    private ArrayList<String> titles;
    private ArrayList<String> images;
    private OnFilmListener mOnFilmListener;

    public Adapter(Context context, ArrayList<String> titles, ArrayList<String> images, OnFilmListener onFilmListener) {
        this.context = context;
        this.titles = titles;
        this.images = images;

        this.mOnFilmListener = onFilmListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_film, parent, false);
        return new ViewHolder(view, mOnFilmListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleFilm.setText(titles.get(position));
        Glide.with(context).load(images.get(position)).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return titles.size();
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
        }
    }

    public interface OnFilmListener{
        void onFilmClick(int position);
    }

}