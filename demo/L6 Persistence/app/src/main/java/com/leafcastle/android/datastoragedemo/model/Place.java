package com.leafcastle.android.datastoragedemo.model;

/**
 * Created by kasper on 24/04/16.
 */
public class Place {

    private long id;
    private String name;

    public Place(){

    }

    public Place(String name){
        this.name = name;
    }

    public Place(long id, String name){
        this(name);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
