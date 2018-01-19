package com.att.m2x.android.services.model;


public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    private String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public String direction() {
        return direction;
    }
}