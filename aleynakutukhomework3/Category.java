package edu.sabanciuniv.aleynakutukhomework3;

import java.io.Serializable;

public class Category implements Serializable {

    private String categoryName;
    private int categoryID;

    public Category() {
    }

    public Category( int categoryID, String categoryName) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
