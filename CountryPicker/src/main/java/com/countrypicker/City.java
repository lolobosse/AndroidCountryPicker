package com.countrypicker;

/**
 * Created by Mark O'Sullivan on 05/04/17.
 */

public class City implements CountryPickerModel {


    private String name;

    public City(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getSearchableString() {
        return name;
    }

    @Override
    public Boolean hasID() {
        return false;
    }

    @Override
    public String getID() {
        return null;
    }
}
