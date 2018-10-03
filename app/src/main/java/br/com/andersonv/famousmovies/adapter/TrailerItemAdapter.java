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
import br.com.andersonv.famousmovies.data.Trailer;

public class TrailerItemAdapter extends ArrayAdapter<Trailer> {

    public TrailerItemAdapter(@NonNull Context context, @NonNull List<Trailer> trailers) {
        super(context, 0, trailers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
        }

        final Trailer trailer = getItem(position);

        final TextView tvTrailerTitle = convertView.findViewById(R.id.tvTrailerTitle);
        tvTrailerTitle.setText(trailer.getName());

        return convertView;
    }
}
