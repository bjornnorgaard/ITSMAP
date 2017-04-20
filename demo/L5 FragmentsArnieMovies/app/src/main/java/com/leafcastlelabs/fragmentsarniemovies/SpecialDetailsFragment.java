package com.leafcastlelabs.fragmentsarniemovies;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialDetailsFragment extends Fragment {

    private MovieSelectorInterface movieSelector;

    private ImageView imgPoster;

    public SpecialDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_special_details, container, false);

        imgPoster = (ImageView)v.findViewById(R.id.imgPoster);
        imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieSelector!=null){

                 //   movieSelector.viewSpecial();
                }
            }
        });

        return v;
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
