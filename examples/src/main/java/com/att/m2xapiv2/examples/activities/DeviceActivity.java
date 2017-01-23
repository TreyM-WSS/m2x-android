package com.att.m2xapiv2.examples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.model.Job;
import com.att.m2x.android.network.ApiV2Response;
import com.att.m2xapiv2.examples.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class DeviceActivity extends Activity implements ResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device, menu);
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

    //Test Methods
    public void searchPublicDevicesCatalog(View view) {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("p","1");
        Device.searchPublicCatalog(DeviceActivity.this, params, this);
    }

    public void searchDevices(View view) {

        Device.searchDevices(DeviceActivity.this, null, this);
    }
    public void listDevices(View view) {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("limit","3");
        Device.listDevices(DeviceActivity.this, params, this);
    }

    public void createDevice(View view) {
        JSONObject params = new JSONObject();
        try {
            params.put("name","LVTest");
            params.put("visibility","public");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Device.createDevice(DeviceActivity.this,params,this);
    }

    public void updateDeviceDetails(View view) {
        JSONObject params = new JSONObject();
        try {
            params.put("name","LVTesty");
            params.put("description","test");
            params.put("visibility","public");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Device.updateDeviceDetails(DeviceActivity.this, params, "437cb907799ae7e01309133006806ba3", this);
    }

    public void viewDeviceDetails(View view) {
        Device.viewDeviceDetails(DeviceActivity.this, "437cb907799ae7e01309133006806ba3", this);
    }

    public void readDeviceLocation(View view) {
        Device.readDeviceLocation(DeviceActivity.this, "437cb907799ae7e01309133006806ba3", this);
    }

    public void updateDeviceLocation(View view) {
        try {
            JSONObject obj = new JSONObject("{ \"name\": \"Storage Room\",\n" +
                    "  \"latitude\": -37.9788423562422,\n" +
                    "  \"longitude\": -57.5478776916862,\n" +
                    "  \"timestamp\": \"2014-09-10T19:15:00.756Z\",\n" +
                    "  \"elevation\": 5 }");
            Device.updateDeviceLocation(DeviceActivity.this,obj,"437cb907799ae7e01309133006806ba3",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void listDataStreams(View view) {
        Device.listDataStreams(DeviceActivity.this, "437cb907799ae7e01309133006806ba3", this);
    }

    public void exportValues(View view) {

        Device.exportValues(DeviceActivity.this, "616775bfb0f27b097010bd44f93a7297", null,this);
    }
    public void createUpdateDataStream(View view){
        try {
            JSONObject o = new JSONObject("{ \"unit\": { \"label\": \"celsius\", symbol: \"C\" }}");
            Device.createUpdateDataStreams(DeviceActivity.this,o,"437cb907799ae7e01309133006806ba3","LVStream",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateDataStreamValue(View view){
        try {
            JSONObject o = new JSONObject("{ \"timestamp\": \"2014-10-01T12:00:00Z\", \"value\": 100 }");
            Device.createUpdateDataStreams(DeviceActivity.this,o,"437cb907799ae7e01309133006806ba3","LVStream",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void viewDataStream(View view){
        Device.viewDataStream(DeviceActivity.this,"437cb907799ae7e01309133006806ba3","LVStream",this);
    }

    public void listDataStream(View view){
        Device.listDataStreams(DeviceActivity.this,"437cb907799ae7e01309133006806ba3",this);
    }

    public void dataStreamSampling(View view){
        Device.listDataStreams(DeviceActivity.this,"437cb907799ae7e01309133006806ba3",this);
    }

    public void dataStreamStats(View view){
        Device.dataStreamStats(DeviceActivity.this,"437cb907799ae7e01309133006806ba3","LVStream",this);
    }

    public void postDataStreamValues(View view){
        try {
            JSONObject o = new JSONObject("{ \"values\": [\n" +
                    "  { \"timestamp\": \"2014-09-09T19:15:00.624Z\", \"value\": 32 },\n" +
                    "  { \"timestamp\": \"2014-09-09T20:15:00.522Z\", \"value\": 30 },\n" +
                    "  { \"timestamp\": \"2014-09-09T21:15:00.522Z\", \"value\": 15 } ] }");
            Device.postDataStreamValues(DeviceActivity.this,o,"437cb907799ae7e01309133006806ba3","LVStream",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataStreamValues(View view){
        try {
            JSONObject o = new JSONObject("{ " +
                    "\"from\": \"2014-09-09T19:15:00.624Z\",\n" +
                    "  \"end\": \"2014-09-09T20:15:00.522Z\" }");
            Device.deleteDataStreamValues(DeviceActivity.this,o,"437cb907799ae7e01309133006806ba3","LVStream",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataStream(View view){
        Device.deleteDataStream(DeviceActivity.this,"437cb907799ae7e01309133006806ba3","LVStream",this);
    }

    public void postDeviceUpdates(View view){
        try {
            JSONObject o = new JSONObject("{ \"values\": {\n" +
                    "  \"temperature\": [\n" +
                    "    { \"timestamp\": \"2014-09-09T19:15:00.981Z\", \"value\": 32 },\n" +
                    "    { \"timestamp\": \"2014-09-09T20:15:00.124Z\", \"value\": 30 },\n" +
                    "    { \"timestamp\": \"2014-09-09T21:15:00.124Z\", \"value\": 15 } ],\n" +
                    "  \"humidity\": [\n" +
                    "    { \"timestamp\": \"2014-09-09T19:15:00.874Z\", \"value\": 88 },\n" +
                    "    { \"timestamp\": \"2014-09-09T20:15:00.752Z\", \"value\": 60 },\n" +
                    "    { \"timestamp\": \"2014-09-09T21:15:00.752Z\", \"value\": 75 } ]\n" +
                    "  }\n" +
                    "}");
            Device.postDeviceUpdates(DeviceActivity.this,o,"437cb907799ae7e01309133006806ba3",this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void viewRequestLog(View view){
        Device.viewRequestLog(DeviceActivity.this,"437cb907799ae7e01309133006806ba3",this);
    }

    public void deleteDevice(View view){
        Device.deleteDevice(DeviceActivity.this,"437cb907799ae7e01309133006806ba3",this);
    }

    //API listeners
    @Override
    public void onRequestCompleted(ApiV2Response result, int requestCode) {
        Log.d("APIV2Request", "Completed");
        Log.d("APIV2 - RESULT",result.get_raw());

        if(requestCode == Device.REQUEST_CODE_DEVICE_EXPORT_VALUES && result.get_headers().contains("Location")){
            String location = result.get_headers();
            location = location.substring(location.indexOf("Location="));
            location = location.substring(location.lastIndexOf("s/")+2,location.indexOf(","));
            Log.d("APIV2 - Location Header",location);
            try {
                Thread.sleep(5000);// wait till export is success, then check job status
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Job.viewDetails(DeviceActivity.this, location, this);
        }
    }

    @Override
    public void onRequestError(ApiV2Response error, int requestCode) {
        Log.d("APIV2Request","Error");
        Log.d("APIV2 - ERROR",error.get_raw());
    }
}
