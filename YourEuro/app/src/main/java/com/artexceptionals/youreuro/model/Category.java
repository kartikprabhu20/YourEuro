package com.artexceptionals.youreuro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@Entity(tableName = "category")
public class Category implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int categoryID = 0;

    @ColumnInfo(name = "catagoryName")
    String catagoryName;
    @ColumnInfo(name = "imageID")
    String imageID;

    public Category(String  catagoryName, String imageID) {
        this.catagoryName = catagoryName;
        this.imageID = imageID;
    }

    @Ignore
    public Category(int categoryID, String catagoryName, String imageID) {
        this(catagoryName,imageID);
        this.categoryID = categoryID;
    }

    protected Category(Parcel in) {
        categoryID = in.readInt();
        catagoryName = in.readString();
        imageID = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(categoryID);
        dest.writeString(catagoryName);
        dest.writeString(imageID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryID == category.categoryID &&
                imageID == category.imageID &&
                catagoryName.equalsIgnoreCase(category.catagoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, catagoryName, imageID);
    }
}
