package com.att.m2x.android.listeners;

import com.att.m2x.android.exceptions.M2XApiException;
import com.att.m2x.android.network.ApiV2Response;

/**
 * ATT M2x response listener
 */
public interface ResponseListener {

    /**
     * Invoked when the request has completed its execution without errors
     * @param result The resulting object from the AsyncTask.
     */
    public void onRequestCompleted(ApiV2Response result, int requestCode);


    /**
     * Invoked when the Request has completed its execution with errors
     * @param error The error message.
     */
    public void onRequestError(ApiV2Response error, int requestCode);

    /**
     * Invoked when the Request has hit an exception in its execution
     *
     * @param exception       The error message.
     * @param requestCode
     */
    void onRequestException(M2XApiException exception, int requestCode);
}
