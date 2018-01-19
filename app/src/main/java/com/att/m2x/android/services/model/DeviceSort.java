package com.att.m2x.android.services.model;

/**
 * Created by collinbrown on 10/17/17.
 */
public enum DeviceSort {
    LAST_ACTIVITY("last_activity"),
    NAME("name"),
    SERIAL("serial"),
    CREATED("created");

    private String fieldName;

    DeviceSort(String fieldName) {
        this.fieldName = fieldName;
    }

    public String fieldName() {
        return fieldName;
    }
}
