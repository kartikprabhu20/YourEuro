package com.artexceptionals.youreuro.model;

public class Category {

    Constants catagoryName;
    Constants imageID;

    public Category(Constants catagoryName, Constants imageID) {
        this.catagoryName = catagoryName;
        this.imageID = imageID;
    }


    public Constants getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(Constants catagoryName) {
        this.catagoryName = catagoryName;
    }

    public Constants getImageID() {
        return imageID;
    }

    public void setImageID(Constants imageID) {
        this.imageID = imageID;
    }
}
