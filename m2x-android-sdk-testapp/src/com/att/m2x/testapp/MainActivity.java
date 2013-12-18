package com.att.m2x.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.att.m2x.*;

public class MainActivity extends Activity {

	private static String TEST_MASTER_KEY = "8181c16a0097325041a0c5a55f4fee1d";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        M2X.getInstance().setMasterKey(TEST_MASTER_KEY);
        
        APITester tester = new APITester(this);
        tester.run();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

}
