package com.leafcastlelabs.fragmentsarniemovies;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.leafcastlelabs.fragmentsarniemovies.Util.MovieLoader;
import com.leafcastlelabs.fragmentsarniemovies.adaptors.MovieListAdaptor;
import com.leafcastlelabs.fragmentsarniemovies.model.Movie;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MovieListFragment extends Fragment {


    private ListView movieListView;
    private MovieListAdaptor adaptor;
    private ArrayList<Movie> movies;

    private ImageView imgHeader;

    private MovieSelectorInterface movieSelector;

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movieListView = (ListView) view.findViewById(R.id.listView);
        imgHeader = (ImageView) view.findViewById(R.id.imgHeader);
        imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieSelector!=null){
                    movieSelector.viewSpecial();
                }
            }
        });
        updateMovies();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

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

    private void onMovieSelected(int position){
        if(movieSelector!=null) {
            movieSelector.onMovieSelected(position);
        }
    }

    public void setMovies(ArrayList<Movie> movieList){
        movies = (ArrayList<Movie>) movieList.clone();
    }

    public void updateMovies(){
        if(movieSelector!=null){
            movies = movieSelector.getMovieList();
        }
        if(movies!=null) {
            adaptor = new MovieListAdaptor(getActivity(), movies);
            movieListView.setAdapter(adaptor);

            movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onMovieSelected(position);
                }
            });
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
