package com.att.m2xapiv2.examples.application;

import android.app.Application;

import com.att.m2x.android.main.M2XAPI;


/**
 * Created by Gokul on 1/23/17.
 */
public class M2XApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize communication library
        M2XAPI.initialize(getApplicationContext(), "<YOUR API KEY HERE>");
    }
}
