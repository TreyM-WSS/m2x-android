package com.att.m2x.android.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * ATT M2X Array Utils
 */
public class ArrayUtils {

    /**
     * @param queryString as HashMap<String,String>
     * @return as String
     */
    public static final String mapToQueryString(HashMap<String,String> queryString){
        StringBuilder sb = new StringBuilder();
        try {
            for(HashMap.Entry<String, String> e : queryString.entrySet()){
                if(sb.length() > 0){
                    sb.append('&');
                }
                sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

}
