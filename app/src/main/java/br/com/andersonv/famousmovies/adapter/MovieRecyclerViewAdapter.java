package br.com.andersonv.famousmovies.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.data.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private final List<Movie> mData;
    private final LayoutInflater mInflater;
    private final MovieRecyclerOnClickHandler mClickHandler;

    public MovieRecyclerViewAdapter(Context context, List<Movie> data, MovieRecyclerOnClickHandler clickHandler) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickHandler = clickHandler;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mData.get(position);
        Picasso.with(mInflater.getContext())
                .load(IMAGE_URL + movie.getPosterPath())
                .into(holder.ivMovieImage);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface MovieRecyclerOnClickHandler {
        void onClick(Movie movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mData.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }
}