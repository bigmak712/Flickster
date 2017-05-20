package com.example.bigmak712.flickster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigmak712.flickster.R;
import com.example.bigmak712.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by bigmak712 on 5/13/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private boolean portraitOrientation = true;

    private boolean getPortraitOrientation() { return portraitOrientation; }

    public void setPortraitOrientation(boolean pOri) { this.portraitOrientation = pOri; }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);

        // check the existing view being reused
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        // find the image view
        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
        // clear out image from image view
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);

        // populate data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        // set the image based on the orientation
        // round out the image corners
        if(getPortraitOrientation()) {
            //Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
            Picasso.with(getContext()).load(movie.getPosterPath()).
                    transform(new RoundedCornersTransformation(10, 10)).into(ivImage);
        }
        else {
            //Picasso.with(getContext()).load(movie.getBackdropPath()).into(ivImage);
            Picasso.with(getContext()).load(movie.getBackdropPath()).
                    transform(new RoundedCornersTransformation(10, 10)).into(ivImage);
        }

        // return the view
        return convertView;
    }
}
