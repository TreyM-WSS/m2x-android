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
        
        M2X.getInstance().setMasterKey("8181c16a0097325041a0c5a55f4fee1d");
        
        this.loadFeeds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void loadFeeds() {
    	M2X.getInstance().getFeeds(null, new M2X.FeedResponseListener() {
        	
        	public void onSuccess(ArrayList<Feed> feeds) {
        		Log.d("M2X-TestApp", String.format("Obtained %d feeds", feeds.size()));
        		for (Feed feed : feeds) {
        			Log.d("M2X-TestApp", feed.toString());
        		}
        	}
        	
        	public void onError(String errorMessage) {
        		Log.d("M2X-TestApp", "Failed to obtain feeds: ".concat(errorMessage));
        	}
        	
        });    	
    }
}
