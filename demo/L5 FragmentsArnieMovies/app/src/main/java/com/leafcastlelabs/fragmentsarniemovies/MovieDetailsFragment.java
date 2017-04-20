package com.leafcastlelabs.fragmentsarniemovies;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leafcastlelabs.fragmentsarniemovies.model.Movie;

import java.lang.reflect.Field;


public class MovieDetailsFragment extends Fragment {

    private TextView txtTitle;
    private TextView txtYear;
    private TextView txtDescription;

    private MovieSelectorInterface movieSelector;


    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);


        txtTitle = (TextView) view.findViewById(R.id.txtMovieTitle);
        txtYear = (TextView) view.findViewById(R.id.txtMovieYear);
        txtDescription = (TextView) view.findViewById(R.id.txtMovieDescription);

        updateMovie();

        return view;
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try{
            movieSelector = (MovieSelectorInterface) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement MovieSelectorInterface");
        }

    }

    public void setMovie(Movie movie){
        if(txtTitle!=null && txtYear!=null && txtDescription!=null) {
            txtTitle.setText(movie.getName());
            txtYear.setText(Integer.toString(movie.getYear()));
            txtDescription.setText("Arnie was playing " + movie.getRole());
        }
    }

    private void updateMovie(){

        if(movieSelector!=null){
            setMovie(movieSelector.getCurrentSelection());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        /*NOTE: this is a small hack to fix appearant bug with support library that puts
         * fragment in an illegal state
         * http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
         */

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
