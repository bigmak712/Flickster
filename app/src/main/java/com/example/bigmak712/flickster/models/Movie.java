package com.example.bigmak712.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by bigmak712 on 5/13/17.
 */

@Parcel // annotation indicates class is Parcelable
public class Movie {

    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double voteAverage;

    public String getPosterPath() { return posterPath; }

    public String getBackdropPath() { return backdropPath; }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() { return voteAverage; }

    public Movie(){}

    public Movie (JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.title = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
    }

    public static ArrayList<Movie> fromJSonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
