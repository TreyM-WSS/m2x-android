package com.att.m2xapiv2.examples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.model.Key;
import com.att.m2x.android.network.ApiV2Response;
import com.att.m2xapiv2.examples.R;

import org.json.JSONObject;



public class KeysActivity extends Activity implements ResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keys);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keys, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void listKeys(View view){
        Key.list(KeysActivity.this, null, this);
    }

    public void createKey(View view){
        try{
            JSONObject o = new JSONObject("{ \"name\": \"newkey\",\n" +
                    "  \"permissions\": [\"GET\", \"PUT\"],\n" +
                    "  \"device\": null,\n" +
                    "  \"stream\": null,\n" +
                    "  \"expires_at\": null }");
            Key.create(KeysActivity.this,o,this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void viewKeyDetails(View view){
        Key.viewDetails(this,"443cbdbf9bb1ebd45096c0dcfb406f9c",this);
    }

    public void updateKey(View view){
        try{
            JSONObject o = new JSONObject("{ \"name\": \"newKey\",\n" +
                    "  \"permissions\": [\"GET\"],\n" +
                    "  \"expires_at\": \"2014-10-03T15:37:05.000Z\",\n" +
                    "  \"stream\": \"temperature\",\n" +
                    "  \"origin\": null,\n" +
                    "  \"device_access\": \"public\" }");
            Key.update(this,o,"443cbdbf9bb1ebd45096c0dcfb406f9c",this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void regenerateKey(View view){
        Key.regenerate(this,"443cbdbf9bb1ebd45096c0dcfb406f9c",this);
    }

    public void deleteKey(View view){
        Key.delete(KeysActivity.this,"20e46ffaa5fa8a09408ca4efa868c37e",this);
    }

    @Override
    public void onRequestCompleted(ApiV2Response result, int requestCode) {
        Log.d("APIV2Request", "Completed");
        Log.d("APIV2 - RESULT",result.get_raw());
    }

    @Override
    public void onRequestError(ApiV2Response error, int requestCode) {
        Log.d("APIV2Request","Error");
        Log.d("APIV2 - ERROR",error.get_raw());
    }
}
