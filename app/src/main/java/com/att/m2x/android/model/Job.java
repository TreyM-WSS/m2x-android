package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import java.util.Locale;

/**
 *
 * This API allows M2X users to track the state and progress of asynchronous jobs.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Job {

    public static final int REQUEST_CODE_VIEW_JOB_DETAILS = 7001;

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/jobs#View-Job-Details">View Job Details</a>} endpoint
     * @param context The application Context.
     * @param jobId as String, ID of the job.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void viewDetails(Context context, String jobId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.JOB_VIEW, jobId),
                null,
                listener,
                REQUEST_CODE_VIEW_JOB_DETAILS
        );
    }

}
