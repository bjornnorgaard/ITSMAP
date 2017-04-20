package com.leafcastlelabs.fragmentsarniemovies.model;

/**
 * Created by kasper on 24/04/15.
 */
public class Movie {

    private String name;
    private int year;
    private String role;

    public Movie(String movieName, int releaseYear, String roleInMovie){

        name = movieName;
        year = releaseYear;
        role = roleInMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
