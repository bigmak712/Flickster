package com.example.bigmak712.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bigmak712.flickster.models.Config;
import com.example.bigmak712.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.bigmak712.flickster.MovieListActivity.API_BASE_URL;
import static com.example.bigmak712.flickster.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    final String movieIdKey = "Movie id";
    AsyncHttpClient client;
    String movieKey;
    // movie that will be displayed
    Movie movie;
    // image config
    Config config;

    // view objects
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.ivBackdropImage) ImageView ivBackdropImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        client = new AsyncHttpClient();
        // resolve the view objects
        //tvTitle = (TextView)findViewById(R.id.tvTitle);
        //tvOverview = (TextView)findViewById(tvOverview);
        //rbVoteAverage = (RatingBar)findViewById(rbVoteAverage);

        // unwrap the movie passed in from the intent
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        config = Parcels.unwrap(getIntent().getParcelableExtra(Config.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // get the movie id for youtube
        getYoutubeUrl(movie.getId());

        // set the title and the overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());

        // load image using glide
        Glide.with(this)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .into(ivBackdropImage);
        ivBackdropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = movie.getId().toString();
                if(id != null || id != "") {
                    Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    i.putExtra(movieIdKey, movieKey);
                    startActivity(i);
                }
            }
        });

        // vote average is 0...10, convert to 0...5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        // if voteAverage is greater than 0, divide it by 2, else don't do anything to it
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
    public void getYoutubeUrl(int id) {
        // create the url
        String url = API_BASE_URL + String.format("/movie/%s/videos", id);
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        params.put("movie_id", String.valueOf(id));
        Log.d("DEBUG", "The id is " + String.valueOf(id));
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // get the movie results from the response
                    JSONArray results = response.getJSONArray("results");
                    // get an individual movie trailer from the results
                    JSONObject movieResult = results.getJSONObject(0);
                    // get the key for the movie trailer and set it
                    movieKey = movieResult.optString("key", "");
                    Log.d("DEBUG", "The movie key is " + movieKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
