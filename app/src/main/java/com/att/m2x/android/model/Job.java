package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * This API allows M2X users to track the state and progress of asynchronous jobs.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Job {

    private String id;
    private JobState state;
    private Object output; // TODO type?
    private Object errors; // TODO type?
    private Date start;
    private Date finished;
    private Date created;
    private Date updated;

    public Job() {
    }

    public Job(String id, JobState state, Object output, Object errors, Date start, Date finished,
               Date created, Date updated) {
        this.id = id;
        this.state = state;
        this.output = output;
        this.errors = errors;
        this.start = start;
        this.finished = finished;
        this.created = created;
        this.updated = updated;
    }

    public static Job fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        Job job = new Job();
        job.fromJson(jsonObject);
        return job;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        id = jsonObject.optString("id", null);
        state = JobState.parse(jsonObject.optString("state", null));
        output = jsonObject.opt("output");
        errors = jsonObject.opt("errors");
        try {
            if (!jsonObject.isNull("start"))
                start = DateUtils.stringToDateTime(jsonObject.getString("start"));
            if (!jsonObject.isNull("finished"))
                finished = DateUtils.stringToDateTime(jsonObject.getString("finished"));
            if (!jsonObject.isNull("created"))
                created = DateUtils.stringToDateTime(jsonObject.getString("created"));
            if (!jsonObject.isNull("updated"))
                updated = DateUtils.stringToDateTime(jsonObject.getString("updated"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public enum JobState {
        //queued, working, complete or failed
        QUEUED("queued"),
        WORKING("working"),
        COMPLETE("complete"),
        FAILED("failed");

        private final String state;

        JobState(String state) {
            this.state = state;
        }

        public String state() {
            return state;
        }

        public static JobState parse(String value) {
            if (value == null) return null;
            for (JobState jobState : values()) {
                if (jobState.state.equalsIgnoreCase(value)) return jobState;
            }
            throw new IllegalArgumentException("Invalid Job State: " + value);
        }
    }

    // STATIC ENDPOINTS MOVED TO services.JobService

    public static final int REQUEST_CODE_VIEW_JOB_DETAILS = 7001;


    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.JobService#viewJobDetails(Context, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/jobs#View-Job-Details">View Job Details</a>} endpoint
     * @param context The application Context.
     * @param jobId as String, ID of the job.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDetails(Context context, String jobId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.JOB_VIEW, jobId),
                null,
                listener,
                REQUEST_CODE_VIEW_JOB_DETAILS
        );
    }

}
