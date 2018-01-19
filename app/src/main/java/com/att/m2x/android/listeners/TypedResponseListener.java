package com.att.m2x.android.listeners;

import com.att.m2x.android.exceptions.M2XApiException;
import com.att.m2x.android.model.Response;

/**
 * ATT M2X response listener
 *
 * Related to {@see <a href="https://github.com/attm2x/m2x-android/blob/master/app/src/main/java/com/att/m2x/android/listeners/ResponseListener.java">m2x-android ResponseListener</a>}
 */
public interface TypedResponseListener<T> {

    /**
     * Invoked when the request has completed its execution without errors
     *
     * @param result      The resulting object from the AsyncTask.
     */
    void onRequestCompleted(T result);


    /**
     * Invoked when the request has completed its execution with errors
     *
     * @param response
     */
    void onRequestError(Response response);


    /**
     * Invoked when the Request has hit an exception in its execution
     *
     * @param exception       The error message.
     */
    void onRequestException(M2XApiException exception);
}
