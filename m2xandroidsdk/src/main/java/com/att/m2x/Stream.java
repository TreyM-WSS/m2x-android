package com.att.m2x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.DateHelper;
import com.att.m2x.helpers.JSONHelper;
import com.att.m2x.helpers.JSONSerializable;

public final class Stream extends com.att.m2x.model.Stream implements JSONSerializable {

	public interface StreamsListener {
		public void onSuccess(ArrayList<Stream> streams);
		public void onError(String errorMessage);
	}

	public interface StreamListener {
		public void onSuccess(Stream stream);
		public void onError(String errorMessage);
	}

	public interface ValuesListener {
		public void onSuccess(ArrayList<StreamValue> values);
		public void onError(String errorMessage);
	}
 
	public interface BasicListener {
		public void onSuccess();
		public void onError(String errorMessage);		
	}
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String LATESTVALUEAT = "latest_value_at";
	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String UNIT = "unit";
	private static final String URL = "url";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";
	private static final String VALUES_KEY = "values";
    private static final String FROM_KEY = "from";
    private static final String END_KEY = "end";

	public Stream() {
		
	}
	
	public Stream(Parcel in) {
		super(in);
	}
		
	public Stream(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));
		this.setLatestValueAt(JSONHelper.dateValue(obj, LATESTVALUEAT, null));
		this.setMin(JSONHelper.doubleValue(obj, MIN, 0));
		this.setMax(JSONHelper.doubleValue(obj, MAX, 0));
		try {
			Unit unit = new Unit(obj.getJSONObject(UNIT));
			this.setUnit(unit);
		} catch (JSONException e) {
		}
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}
	
	public static void getStreams(Context context, String feedKey, String feedId, final StreamsListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/streams";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Stream> array = new ArrayList<Stream>();
				try {
					JSONArray streams = object.getJSONArray("streams");
					for (int i = 0; i < streams.length(); i++) {
						Stream stream = new Stream(streams.getJSONObject(i));
						array.add(stream);
					}
				} catch (JSONException e) {
					M2XLog.d("Failed to parse json objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public static void getStream(Context context, String feedKey, String feedId, String streamName, final StreamListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String cleanStreamName = streamName.replace(" ", "_");
		String path = "/feeds/" + feedId + "/streams/" + cleanStreamName;
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				Stream stream = new Stream(object);
				callback.onSuccess(stream);
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});

	}
	
	public void update(Context context, String feedKey, String feedId, final BasicListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String cleanName =  this.getName().replace(" ", "_");
		String path = "/feeds/" + feedId + "/streams/" + cleanName;

        if (this.getName() != null) {
            JSONObject content = this.toJSONObject();
            client.put(context,
                    feedKey,
                    path,
                    content,
                    new M2XHttpClient.Handler() {

                        @Override
                        public void onSuccess(int statusCode, JSONObject object) {
                            callback.onSuccess();
                        }

                        @Override
                        public void onFailure(int statusCode, String message) {
                            callback.onError(message);
                        }

                    });
        } else {
            callback.onError("Stream name is required attribute!");
        }
		
	}
		
	public void getValues(Context context, String feedKey, String feedId, HashMap<String, String> params, final ValuesListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String cleanStreamName = this.getName().replace(" ", "_");
		String path = "/feeds/" + feedId + "/streams/" + cleanStreamName + "/values";
		
		client.get(context, feedKey, path, params, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<StreamValue> array = new ArrayList<StreamValue>();
				try {
					JSONArray values = object.getJSONArray(VALUES_KEY);
					for (int i = 0; i < values.length(); i++) {
						StreamValue value = new StreamValue(values.getJSONObject(i));
						array.add(value);
					}
				} catch (JSONException e) {
					M2XLog.d("Failed to parse StreamValue JSON objects");
				}
				callback.onSuccess(array);
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}

	public void setValues(Context context, String feedKey, String feedId, ArrayList<StreamValue> values, final BasicListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String cleanStreamName =  this.getName().replace(" ", "_");
		String path = "/feeds/" + feedId + "/streams/" + cleanStreamName + "/values";
		
		try {
			boolean emptyDate = false;

			JSONObject content = new JSONObject();
			JSONArray array = new JSONArray();
			for (StreamValue value : values) {
                if (value.getDate() != null)
		            array.put(value.toJSONObject());
                else
                    emptyDate = true;
			}

            if (!emptyDate) {
                content.put(VALUES_KEY, array);

                client.post(context,
                        feedKey,
                        path,
                        content,
                        new M2XHttpClient.Handler() {

                            @Override
                            public void onSuccess(int statusCode, JSONObject object) {
                                callback.onSuccess();
                            }

                            @Override
                            public void onFailure(int statusCode, String message) {
                                callback.onError(message);
                            }

                        });
            } else {
                callback.onError("Date attribute is not present!");
            }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void delete(Context context, String feedKey, String feedId, final BasicListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String cleanStreamName =  this.getName().replace(" ", "_");
		String path = "/feeds/" + feedId + "/streams/" + cleanStreamName;

		client.delete(context, 
				feedKey,
				path, 
				new M2XHttpClient.Handler() {

					@Override
					public void onSuccess(int statusCode, JSONObject object) {
						callback.onSuccess();
					}

					@Override
					public void onFailure(int statusCode, String message) {						
						callback.onError(message);
					}
			
		});
		
	}

    public void deleteValuesByRange(Context context, String feedKey, String feedId, Date from, Date end, final BasicListener callback) {

        M2XHttpClient client = M2X.getInstance().getClient();
        String cleanStreamName =  this.getName().replace(" ", "_");
        String path = "/feeds/" + feedId + "/streams/" + cleanStreamName + "/values";

        String from_ = DateHelper.dateToString(from);
        String end_ = DateHelper.dateToString(end);

        HashMap data = new HashMap();
        data.put(FROM_KEY, from_);
        data.put(END_KEY, end_);

        client.delete(context,
                feedKey,
                path,
                data,
                new M2XHttpClient.Handler() {

                    @Override
                    public void onSuccess(int statusCode, JSONObject object) {
                        callback.onSuccess();
                    }

                    @Override
                    public void onFailure(int statusCode, String message) {
                        callback.onError(message);
                    }

                });

    }
	
	public JSONObject toJSONObject() {
		
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, NAME, this.getName());
		JSONHelper.put(obj, UNIT, ((Unit) this.getUnit()).toJSONObject() );
		return obj;
	}
	
	public static final Parcelable.Creator<Stream> CREATOR = new Parcelable.Creator<Stream>() {
	    public Stream createFromParcel(Parcel in) {
	     return new Stream(in);
	    }

	    public Stream[] newArray(int size) {
	     return new Stream[size];
	    }
	};
	
}
