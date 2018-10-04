package br.com.andersonv.famousmovies.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

import br.com.andersonv.famousmovies.R;
import br.com.andersonv.famousmovies.data.Trailer;

public class TrailerItemAdapter extends ArrayAdapter<Trailer> {

    private static final String IMAGE_YOUTUBE_DEFAULT_THUMB = "https://img.youtube.com/vi/{0}/default.jpg";
    private final Context context;

    public TrailerItemAdapter(@NonNull Context context, @NonNull List<Trailer> trailers) {
        super(context, 0, trailers);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
        }

        final Trailer trailer = getItem(position);

        final TextView tvTrailerTitle = convertView.findViewById(R.id.tvTrailerTitle);
        final ImageView ivThumbVideo = convertView.findViewById(R.id.ivThumbVideo);

        tvTrailerTitle.setText(trailer.getName());

        String imageThumb = MessageFormat.format(IMAGE_YOUTUBE_DEFAULT_THUMB, trailer.getKey());

        Picasso.with(parent.getContext())
                .load(imageThumb)
                .into(ivThumbVideo);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }
                }
        });

        return convertView;
    }
}
