package br.com.andersonv.famousmovies.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.database.FavoriteEntry;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    private List<FavoriteEntry> mFavoritesEntries;
    private Context mContext;
    private final LayoutInflater mInflater;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public FavoriteRecyclerViewAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favorite_item, parent, false);


        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        FavoriteEntry favoriteEntry = mFavoritesEntries.get(position);

        Picasso.with(mInflater.getContext())
                .load(IMAGE_URL + favoriteEntry.getPoster())
                .into(holder.ivMovieImage);

        String title = favoriteEntry.getTitle();
        String overview = favoriteEntry.getOverview();
        String voteAverage = MessageFormat.format("{0, number,#.##}/10", favoriteEntry.getVoteAverage());

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String releaseDate = sdf.format(favoriteEntry.getReleaseDate());

        holder.tvTitle.setText(title);
        holder.tvOverview.setText(overview);
        holder.tvVoteAverage.setText(voteAverage);
        holder.tvReleaseDate.setText(releaseDate);
    }

    @Override
    public int getItemCount() {
        if (mFavoritesEntries == null) {
            return 0;
        }
        return mFavoritesEntries.size();
    }

    public List<FavoriteEntry> getFavorites() {
        return mFavoritesEntries;
    }


    public void setFavorites(List<FavoriteEntry> favoritesEntries) {
        mFavoritesEntries = favoritesEntries;
        notifyDataSetChanged();
    }


    class FavoriteViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvOverview)
        TextView tvOverview;

        @BindView(R.id.tvVoteAverage)
        TextView tvVoteAverage;

        @BindView(R.id.tvReleaseDate)
        TextView tvReleaseDate;

        FavoriteViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}