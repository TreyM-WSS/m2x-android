package com.att.m2x.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.att.m2x.Log;
import com.att.m2x.model.*;

public class FeedDetailActivity extends Activity {

	public static final String INPUT_SELECTED_FEED = "com.att.m2x.Feed.SelectedFeed";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			Feed feed = bundle.getParcelable(INPUT_SELECTED_FEED);
			Log.d("Got feed: " + feed.toString() );
		} else {
			finish();			
		}
		
		setContentView(R.layout.activity_feed_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_detail, menu);
		return true;
	}

}
