package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 2/10/2017.
 */

public class Transport {
    private String name;
    private int picture;

    public Transport(){

    }

    public Transport(String name, int picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "name='" + name + '\'' +
                ", picture=" + picture +
                '}';
    }
}
