package com.example.bigmak712.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bigmak712.flickster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    // movie that will be displayed
    Movie movie;

    // view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar)findViewById(R.id.rbVoteAverage);

        // unwrap the movie passed in from the intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and the overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0...10, convert to 0...5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        // if voteAverage is greater than 0, divide it by 2, else don't do anything to it
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}
