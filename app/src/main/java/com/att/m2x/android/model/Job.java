package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import java.util.Locale;

/**
 * Created by kristinpeterson on 12/28/15.
 */
public class Job {

    public static final int REQUEST_CODE_VIEW_JOB_DETAILS = 7001;
    public static final int REQUEST_CODE_VIEW_JOB_RESULTS = 7002;

    public static final void viewDetails(Context context, String jobId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.JOB_VIEW, jobId),
                null,
                listener,
                REQUEST_CODE_VIEW_JOB_DETAILS
        );
    }

    public static final void viewResults(Context context, String jobId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.JOB_VIEW, jobId),
                null,
                listener,
                REQUEST_CODE_VIEW_JOB_RESULTS
        );
    }

}
