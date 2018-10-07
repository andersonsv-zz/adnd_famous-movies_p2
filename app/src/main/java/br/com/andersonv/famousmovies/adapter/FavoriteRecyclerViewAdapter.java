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
import br.com.andersonv.famousmovies.database.FavoriteEntry;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private final List<FavoriteEntry> mData;
    private final LayoutInflater mInflater;

    public FavoriteRecyclerViewAdapter(Context context, List<FavoriteEntry> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.favorite_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteEntry favorite = mData.get(position);
        Picasso.with(mInflater.getContext())
                .load(IMAGE_URL + favorite.getPoster())
                .into(holder.ivMovieImage);

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}