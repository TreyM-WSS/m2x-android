package com.att.m2x.android.services;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Job;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * This API allows M2X users to track the state and progress of asynchronous jobs.
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/jobs">M2X Jobs REST API</a>}
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class JobService {
    public static final int REQUEST_CODE_VIEW_JOB_DETAILS = 7001;

    private JobService() {
        // don't instantiate
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/jobs#View-Job-Details">View Job Details</a> endpoint
     * @param context
     * @param jobId
     * @param listener
     */
    public static void viewJobDetails(Context context, String jobId,
                                      TypedResponseListener<Job> listener) {
        viewJobDetails(context, jobId, new JobResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/jobs#View-Job-Details">View Job Details</a> endpoint
     * @param context
     * @param jobId
     * @param listener
     */
    public static void viewJobDetails(Context context, String jobId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.JOB_VIEW, jobId),
                null,
                listener,
                REQUEST_CODE_VIEW_JOB_DETAILS
        );
    }

    private static class JobResponseListener extends BaseResponseListener<Job> {
        JobResponseListener(TypedResponseListener<Job> listener) {
            super(listener);
        }

        @Override
        Job parseResponse(JSONObject jsonObject) throws JSONException {
            return Job.fromJsonObject(jsonObject);
        }
    }
}
