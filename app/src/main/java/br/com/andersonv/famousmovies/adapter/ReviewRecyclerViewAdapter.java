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
import br.com.andersonv.famousmovies.data.Review;
import br.com.andersonv.famousmovies.data.Trailer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private final List<Review> mData;
    private final LayoutInflater mInflater;

    public ReviewRecyclerViewAdapter(Context context, List<Review> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = mData.get(position);

        //TODO - colocar os dados em variaveis
        holder.tvReviewAuthor.setText(review.getAuthor());
        holder.tvReviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() { return mData != null ? mData.size() : 0; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvReviewAuthor)
        TextView tvReviewAuthor;

        @BindView(R.id.tvReviewContent)
        TextView tvReviewContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}