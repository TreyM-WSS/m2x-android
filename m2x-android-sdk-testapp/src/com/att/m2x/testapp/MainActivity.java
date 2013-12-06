package com.att.m2x.testapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import com.att.m2x.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.d("test", "Getting all available feeds");
        M2X m2x = M2X.getInstance();
        m2x.setMasterKey("8181c16a0097325041a0c5a55f4fee1d");
        m2x.getAllFeeds(new ResponseListener() {
        	
        	public void onSuccess(ArrayList<Object> items) {
        		Log.d("test", String.format("Obtained %d feeds", items.size()));
        	}
        	
        	public void onError(String errorMessage) {
        		Log.d("test", "Failed to obtain feeds: ".concat(errorMessage));
        	}
        	
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
