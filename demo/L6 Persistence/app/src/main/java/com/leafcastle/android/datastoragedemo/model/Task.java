package com.leafcastle.android.datastoragedemo.model;

/**
 * Created by kasper on 24/04/16.
 */

public class Task {

    private long id;
    private String name;
    private Place place;
    private int status;
    private String created;
    private String deadline;

    public Task(){

    }

    public Task(String name, Place place, int status, String deadline){
        this.name = name;
        this.place = place;
        this.status = status;
        this.deadline = deadline;
        this.created = "";
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String prettyPrint(){
        String s = "";
        s += this.name;
        if(place!=null){
            s+= " at " + place.getName()!=null ? place.getName() : "unknown";
        }
        s += "(" + this.id + ")";
        return s;
    }
}
