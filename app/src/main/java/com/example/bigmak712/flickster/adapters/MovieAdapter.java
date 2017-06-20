package com.example.bigmak712.flickster.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bigmak712.flickster.R;
import com.example.bigmak712.flickster.models.Config;
import com.example.bigmak712.flickster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by bigmak712 on 5/13/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // list of movies
    ArrayList<Movie> movies;
    // config needed for image urls
    Config config;
    // context for rendering
    Context context;

    // initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) { this.config = config; }

    public Config getConfig() { return config; }

    private boolean portraitOrientation = true;

    private boolean getPortraitOrientation() { return portraitOrientation; }

    public void setPortraitOrientation(boolean pOri) { this.portraitOrientation = pOri; }

    // creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // return a new ViewHolder
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the movie data at the specified position
        Movie movie = movies.get(position);
        // populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        // build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.ivPosterImage);
    }

    // returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivMovieImage);
            tvOverview = (TextView)itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void onClick(View v) {
            // get the item's position

        }
    }

/*
    public MovieAdapter(Context context, List<Movie> movies) {
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

        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());


        /*
        // find the image view
        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
        // clear out image from image view
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);

        // populate data
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        */
/*
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
 */
}
