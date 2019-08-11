package com.socfire.socialfeed.tamilinsta;

public class DataModel {

    String name;
    String version;
    int id_;
    int image;

    public DataModel(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public DataModel(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}