package br.com.andersonv.famousmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.data.Review;
import br.com.andersonv.famousmovies.data.Trailer;

public class ReviewItemAdapter extends ArrayAdapter<Review> {

    public ReviewItemAdapter(@NonNull Context context, @NonNull List<Review> trailers) {
        super(context, 0, trailers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        //TODO - incluir butterknife
        final Review review = getItem(position);

        final TextView tvReviewTitle = convertView.findViewById(R.id.tvReviewAuthor);
        final TextView tvReviewContent = convertView.findViewById(R.id.tvReviewContent);

        tvReviewTitle.setText(review.getAuthor());
        tvReviewContent.setText(review.getContent());

        return convertView;
    }
}
