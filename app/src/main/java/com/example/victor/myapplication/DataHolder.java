package com.example.victor.myapplication;

/**
 * Created by Leroy Arthus on 21/12/2016.
 */

public class DataHolder {
    private String data;
    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}