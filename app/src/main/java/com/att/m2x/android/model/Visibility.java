package com.att.m2x.android.model;

/**
 * Created by collinbrown on 10/17/17.
 */
public enum Visibility {
    PUBLIC("public"),
    PRIVATE("private");

    private final String visibility;

    Visibility(String visibility) {
        this.visibility = visibility;
    }

    public String visibility() {
        return visibility;
    }

    public static Visibility parse(String value) {
        if (value == null) return null;
        switch (value.toLowerCase()) {
            case "public":
                return PUBLIC;
            case "private":
                return PRIVATE;
        }
        throw new IllegalArgumentException("Invalid Visibility: " + value);
    }
}
