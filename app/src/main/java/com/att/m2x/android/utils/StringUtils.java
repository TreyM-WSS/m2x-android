package com.att.m2x.android.utils;

import java.util.List;

public class StringUtils {

    public static Boolean hasText(String string) {
        return !(string == null || string.trim().length() == 0);
    }

    public static <T> String join(Iterable<T> itr, String separator) {
        if (itr != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String _separator = "";
            for (T value : itr) {
                stringBuilder.append(_separator).append(value);
                _separator = separator;
            }
            return stringBuilder.toString();
        }
        else {
            return null;
        }
    }
}
