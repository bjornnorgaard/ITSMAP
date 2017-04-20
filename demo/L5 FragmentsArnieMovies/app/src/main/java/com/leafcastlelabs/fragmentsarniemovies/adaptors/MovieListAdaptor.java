package com.leafcastlelabs.fragmentsarniemovies.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leafcastlelabs.fragmentsarniemovies.R;
import com.leafcastlelabs.fragmentsarniemovies.model.Movie;

import java.util.ArrayList;

/**
 * Created by kasper on 24/04/15.
 */
public class MovieListAdaptor extends BaseAdapter{

    Context context;
    ArrayList<Movie> movies;
    Movie movie = null;

    public MovieListAdaptor(Context c, ArrayList<Movie> movieList){
        movies = movieList;
        context = c;
    }

    @Override
    public int getCount() {
        if(movies!=null) {
            return movies.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(movies!=null){
            return movies.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater movieInflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = movieInflator.inflate(R.layout.movie_list_item, null);
        }

        movie = movies.get(position);
        if(movie!=null){
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            txtTitle.setText(movie.getName());

            int year = movie.getYear();


            TextView txtDescription = (TextView) convertView.findViewById(R.id.txtYear);
            txtDescription.setText(Integer.toString(movie.getYear()));
        }
        return convertView;
    }
}
