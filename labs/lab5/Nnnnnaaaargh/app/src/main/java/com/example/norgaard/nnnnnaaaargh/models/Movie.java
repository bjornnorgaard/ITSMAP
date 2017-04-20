package com.example.norgaard.nnnnnaaaargh.models;

public class Movie {

    private String name;
    private String role;
    private int year;

    Movie(String movieName, int releaseYear, String roleInMovie) {

        role = roleInMovie;
        year = releaseYear;
        name = movieName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
