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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.example.bigmak712.flickster.R;
import com.example.bigmak712.flickster.models.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.bigmak712.flickster.R.id.tvOverview;
import static com.example.bigmak712.flickster.R.id.tvTitle;

/**
 * Created by bigmak712 on 5/13/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private class ViewHolder implements View.OnClickListener{
        ImageView movieImage;
        TextView title;
        TextView overview;

        @Override
        public void onClick(View v) {
            // get the item's position

        }
    }

    private boolean portraitOrientation = true;

    private boolean getPortraitOrientation() { return portraitOrientation; }

    public void setPortraitOrientation(boolean pOri) { this.portraitOrientation = pOri; }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for position
        Movie movie = getItem(position);

        // View lookup cache stored in tag
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {

            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            // Set the fields in the viewHolder
            viewHolder.movieImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
            viewHolder.title = (TextView)convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView)convertView.findViewById(tvOverview);

            // Load placeholder image?
            Glide.with(getContext())
                    .load(R.drawable.ic_movie_placeholder)
                    .override(200, 400)
                    .into(viewHolder.movieImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());


        /*
        // find the image view
        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
        // clear out image from image view
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);

        // populate data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
        */

        // Transformation to add rounded corners to the movie image
        Transformation roundedCorners = new RoundedCornersTransformation(getContext(), 10, 10);

        // Set the image based on the orientation & round out the image corners
        if(getPortraitOrientation()) {
            //Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
            Glide.with(getContext())
                    .load(movie.getPosterPath())
                    .bitmapTransform(roundedCorners)
                    .into(viewHolder.movieImage);
        }
        else {
            //Picasso.with(getContext()).load(movie.getBackdropPath()).into(ivImage);
            Glide.with(getContext())
                    .load(movie.getBackdropPath())
                    .bitmapTransform(roundedCorners)
                    .into(viewHolder.movieImage);
        }

        // return the view
        return convertView;
    }
}
