package com.leafcastlelabs.fragmentsarniemovies;

import com.leafcastlelabs.fragmentsarniemovies.model.Movie;

import java.util.ArrayList;

/**
 * Created by kasper on 24/04/15.
 */
public interface MovieSelectorInterface{

    public void onMovieSelected(int position);
    public ArrayList<Movie> getMovieList();
    public Movie getCurrentSelection();
    public void viewSpecial();
}
