package com.leafcastlelabs.fragmentsarniemovies.Util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.leafcastlelabs.fragmentsarniemovies.model.Movie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by kasper on 24/04/15.
 */
public class MovieLoader {

    Activity activity;

    public MovieLoader(Activity a){
        activity = a;
    }

    public ArrayList<Movie> getMovieList(){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Movie temp;
        InputStream is = activity.getResources().openRawResource(activity.getResources().getIdentifier("raw/arniemovies",
                "raw", activity.getPackageName()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                if(items.length==3){
                    try {
                        temp = new Movie(items[0], Integer.parseInt(items[1]), items[2]);
                        movies.add(temp);
                    } catch (NumberFormatException ex) {
                        Log.e("ERROR", "Bad format of number, ex");
                    } catch (NullPointerException ex) {
                        Log.e("ERROR", "Null value", ex);
                    } catch (Exception ex){
                        Log.e("ERROR", "Something crazy happened", ex);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", "Something wrong during CSV file read", ex);
        }

        return movies;
    }

}
