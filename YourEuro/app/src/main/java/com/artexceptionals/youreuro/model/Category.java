package com.artexceptionals.youreuro.model;

public class Category {

    String catagoryName;
    String imageID;

    public Category(String  catagoryName, String imageID) {
        this.catagoryName = catagoryName;
        this.imageID = imageID;
    }


    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
