package com.att.m2x.testapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.att.m2x.helpers.DateHelper;
import com.att.m2x.*;

import org.json.JSONObject;

public class FeedDetailActivity extends Activity implements AdapterView.OnItemClickListener {

	public static final String INPUT_SELECTED_FEED = "com.att.m2x.Feed.SelectedFeed";
	
	private Feed feed;
	private TextView name;
	private TextView description;
	private TextView visibility;
	private TextView status;
	private TextView type;
	private TextView created;
	private TextView updated;	
	private TextView location;

    private ListView streamList;
    private StreamArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_feed_detail);

		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			feed = bundle.getParcelable(INPUT_SELECTED_FEED);
			
			name = (TextView) findViewById(R.id.name);
			description = (TextView) findViewById(R.id.description);
			visibility = (TextView) findViewById(R.id.visibility);
			status = (TextView) findViewById(R.id.status);
			type = (TextView) findViewById(R.id.type);
			created = (TextView) findViewById(R.id.created);
			updated = (TextView) findViewById(R.id.updated);
			location = (TextView) findViewById(R.id.location);
			
			name.setText(feed.getName());
			description.setText(feed.getDescription());
			visibility.setText(feed.getVisibility());
			status.setText(feed.getStatus());
			type.setText(feed.getType());
			created.setText(DateHelper.dateToString(feed.getCreated()));
			updated.setText(DateHelper.dateToString(feed.getUpdated()));

			Location loc = feed.getLocation();
			location.setText((loc != null) ? String.format(Locale.US, "%.5f, %.5f", loc.getLatitude(), loc.getLongitude()) : "Unknown");

            streamList = (ListView) findViewById(R.id.streamView);
            streamList.setOnItemClickListener(this);
            this.loadStreams();

		} else {
			finish();			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_detail, menu);
		return true;
	}

    private void loadStreams() {
        Stream.getStreams(this, null, feed.getId(), new Stream.StreamsListener() {
            public void onSuccess(ArrayList<Stream> streams) {
                adapter = new StreamArrayAdapter(FeedDetailActivity.this, streams);
                streamList.setAdapter(adapter);
            }

            public void onError(String errorMessage) {
                Log.d("m2x-test", "Failed to load streams! " + errorMessage);
            }
        });
    }

    private class StreamArrayAdapter extends ArrayAdapter<Stream> {

        private Context context;
        private static final int LAYOUT_RESOURCE = R.layout.activity_feed_listitem;
        private ArrayList<Stream> objects;

        public StreamArrayAdapter(Context context, ArrayList<Stream> objects) {
            super(context, LAYOUT_RESOURCE, objects);
            this.objects = objects;
            this.context = context;
        }

        public ArrayList<Stream> getObjects() {
            return objects;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(LAYOUT_RESOURCE, null);
            }

            Stream stream = getItem(position);
            if (stream != null) {
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setText(stream.getName());

                TextView symbol = (TextView) view.findViewById(R.id.symbol);
                symbol.setText("S");
                symbol.setBackgroundResource(R.color.batch_symbol_background);

                ImageButton addButton = (ImageButton) view.findViewById(R.id.addButton);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addValues(position);
                    }
                });

                ImageButton removeButton = (ImageButton) view.findViewById(R.id.removeButton);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeValues(position);
                    }
                });

                ImageButton deleteStreamButton = (ImageButton) view.findViewById(R.id.deleteStreamButton);
                deleteStreamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteStream(position);
                    }
                });
            }

            return view;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // ...
    }

    public void addValues(int position) {
        Random randomGenerator = new Random();
        ArrayList<StreamValue> readings = new ArrayList<StreamValue>();
        for (int i = 0 ; i < 5 ; i++) {
            double reading = randomGenerator.nextDouble() * 100;
            Date date = new Date();
            StreamValue value = new StreamValue();
            value.setDate(date);
            value.setValue(reading);
            readings.add(value);
        }

        List<Stream> objects = adapter.getObjects();
        Stream selectedStream = objects.get(position);
        selectedStream.setValues(this, null, feed.getId(), readings, new Stream.BasicListener() {
            public void onSuccess() {
                Log.d("m2x-test", "Stream values added successfully");
            }

            public void onError(String errorMessage) {
                Log.d("m2x-test", "Failed to add values to the stream! " + errorMessage);
            }
        });
    }

    public void removeValues(int position) {
        List<Stream> objects = adapter.getObjects();
        Stream selectedStream = objects.get(position);
        Date from = DateHelper.stringToDate("2014-09-02T00:00:00.000Z");
        Date end = DateHelper.stringToDate("2014-09-02T14:50:51.314Z");
        selectedStream.deleteValuesByRange(this, null, feed.getId(), from, end, new Stream.BasicListener() {
            public void onSuccess() {
                Log.d("m2x-test", "Stream values deleted successfully");
            }

            public void onError(String errorMessage) {
                Log.d("m2x-test", "Failed to delete stream values by range! " + errorMessage);
            }
        });
    }

    public void deleteStream(int position) {
        List<Stream> objects = adapter.getObjects();
        Stream selectedStream = objects.get(position);
        selectedStream.delete(this, null, feed.getId(), new Stream.BasicListener() {
            public void onSuccess() {
                Log.d("m2x-test", "Stream deleted successfully");
            }

            public void onError(String errorMessage) {
                Log.d("m2x-test", "Failed to delete stream! " + errorMessage);
            }
        });
    }
}
