package com.artexceptionals.youreuro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int categoryID = 0;

    @ColumnInfo(name = "catagoryName")
    String catagoryName;
    @ColumnInfo(name = "imageID")
    int imageID;

    public Category(String  catagoryName, int imageID) {
        this.catagoryName = catagoryName;
        this.imageID = imageID;
    }

    @Ignore
    public Category(int categoryID, String catagoryName, int imageID) {
        this(catagoryName,imageID);
        this.categoryID = categoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
