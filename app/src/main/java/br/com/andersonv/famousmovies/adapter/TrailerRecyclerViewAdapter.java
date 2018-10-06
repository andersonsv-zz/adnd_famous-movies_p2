package br.com.andersonv.famousmovies.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.activity.MovieDetailActivity;
import br.com.andersonv.famousmovies.data.Movie;
import br.com.andersonv.famousmovies.data.Trailer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {

    private final List<Trailer> mData;
    private final LayoutInflater mInflater;
    private final TrailerRecyclerOnClickHandler mClickHandler;

    private static final String IMAGE_YOUTUBE_DEFAULT_THUMB = "https://img.youtube.com/vi/{0}/default.jpg";

    public TrailerRecyclerViewAdapter(Context context, List<Trailer> data, TrailerRecyclerOnClickHandler onClickHandler) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickHandler = onClickHandler;
    }

    public interface TrailerRecyclerOnClickHandler {
        void onClick(Trailer trailer);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trailer_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trailer trailer = mData.get(position);

        holder.tvTrailerTitle.setText(trailer.getName());

        String imageThumb = MessageFormat.format(IMAGE_YOUTUBE_DEFAULT_THUMB, trailer.getKey());

        Picasso.with(mInflater.getContext())
                .load(imageThumb)
                .into(holder.ivThumbVideo);

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivThumbVideo)
        ImageView ivThumbVideo;

        @BindView(R.id.tvTrailerTitle)
        TextView tvTrailerTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mData.get(adapterPosition);
            mClickHandler.onClick(trailer);
        }
    }
}