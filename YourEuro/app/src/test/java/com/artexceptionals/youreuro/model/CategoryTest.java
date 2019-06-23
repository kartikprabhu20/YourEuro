package com.artexceptionals.youreuro.model;

import org.junit.Test;
import org.junit.experimental.categories.Categories;

import static org.junit.Assert.*;

public class CategoryTest {
    @Test
    public void constructor1Testing(){
        Category category = new Category("Entertainment","image_movie",true);
        assertEquals("Fetching category name failed","Entertainment",category.getCatagoryName());
        assertEquals("Fetching image ID failed","image_movie",category.getImageID());
    }

    @Test
    public void constructor2Testing(){
        Category category1 = new Category(4,"Bills","image_dollar");
        assertEquals("Fetching category ID failed",4,category1.getCategoryID());
        assertEquals("Fetching category name failed","Bills",category1.getCatagoryName());
        assertEquals("Fetching image ID failed","image_dollar",category1.getImageID());
    }

    @Test
    public void setterGetterTesting(){
        Category category2 = new Category();
        category2.setCategoryID(6);
        category2.setCatagoryName("Shopping");
        category2.setImageID("image_shopping");
        category2.setThreshold(50l);
        assertEquals("Fetching category ID failed",6,category2.getCategoryID());
        assertEquals("Fetching threshold failed",50l,category2.getThreshold());
        assertEquals("Fetching category name failed","Shopping",category2.getCatagoryName());
        assertEquals("Fetching image ID failed","image_shopping",category2.getImageID());
    }
}