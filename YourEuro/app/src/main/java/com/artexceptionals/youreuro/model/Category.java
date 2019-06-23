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

    @ColumnInfo(name = "isDefault")
    public boolean isDefault;

    @ColumnInfo(name = "threshold")
    public long threshold;

    public Category(String  catagoryName, String imageID, boolean isDefault) {
        this.catagoryName = catagoryName;
        this.imageID = imageID;
        this.isDefault = isDefault;
    }

    @Ignore
    public Category(String  catagoryName, String imageID) {
        this(catagoryName,imageID, true);
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
        isDefault = in.readByte() != 0;
        threshold = in.readLong();
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

    public Category() {

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

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
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
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeLong(threshold);
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
