package com.att.m2x.android.network;

import com.att.m2x.android.utils.StringUtils;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Response Class <p>
 */

public class ApiV2Response implements Serializable {

    public ApiV2Response() {

    }

    public ApiV2Response(String response, int status, Map<String, String> headers) {
        this.status = status;
        this.headers = headers;
        this.rawBody = response;
        parseRawBody();
    }

    public ApiV2Response(Exception e) {
        error = e.getMessage();
        if (e.getCause() != null && e.getCause().getMessage() != null) {
            this.raw = e.getCause().getMessage();
        }
        else {
            this.raw = e.getMessage();
        }
        this.status = -1;
        errors = new HashMap<String, String>();
        errors.put(e.getClass().getName(), e.getMessage());
    }

    private String id;
    public String getId() {
        return id;
    }

    /**
     * The raw content of the "body" portion of the response
     */
    private String rawBody = null;
    public String getRawBody() {
        return rawBody;
    }

    /**
     * The JSONObject of the "body" portion of the response
     */
    private JSONObject jsonBody = null;
    public JSONObject getJsonBody() {
        return jsonBody;
    }

    private String error = null;
    public String getError() {
        return error;
    }

    private String description = null;
    public String getDescription() {
        return description;
    }

    private String message = null;
    public String getMessage() {
        return message;
    }

    private Map<String, String> errors = null;
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * raw: The raw response.
     */
    @SerializedName("raw")
    protected String raw;
    /**
     * Gets the raw response body
     * @return as String, the raw response
     */
    public String getRaw() {
        return this.raw;
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getRaw()}
     *
     * Gets the raw response body
     * @return as String, the raw response body
     */
    @Deprecated
    public String get_raw() {
        return this.getRaw();
    }

    /**
     * @deprecated To be removed
     *
     * Sets the raw response body
     * @param _raw as String
     */
    @Deprecated
    public void set_raw(String _raw) {
        this.raw = _raw;
    }

    /**
     * json: The parsed response body.
     */
    @SerializedName("json")
    protected JSONObject jsonObject;
    /**
     * Gets the parsed response body
     * @return as JSON Object, the parsed response body
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getJsonObject()}
     *
     * Gets the parsed response body
     * @return as JSON Object, the parsed response body
     */
    @Deprecated
    public JSONObject get_json() {
        return getJsonObject();
    }

    /**
     * @deprecated To be removed
     *
     * Sets the parsed response body
     * @param _json as JSON Object
     */
    @Deprecated
    public void set_json(JSONObject _json) {
        this.jsonObject = _json;
    }

    /**
     * status: The status code of the response.
     */
    @SerializedName("status")
    protected Integer status;
    /**
     * Get the status code of the response.
     * @return as String, the status code
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getStatus()}
     *
     * Get the status code of the response.
     * @return as String, the status code
     */
    @Deprecated
    public String get_status() {
        if (getStatus() != null) {
            return getStatus().toString();
        }
        else {
            return null;
        }
    }

    /**
     * @deprecated To be removed
     *
     * Set the status code of the response.
     * @param _status, as String
     */
    @Deprecated
    public void set_status(String _status) {
        this.status = (_status != null ? Integer.valueOf(_status) : null);
    }

    /**
     * headers: The headers included on the response.
     */
    @SerializedName("headers")
    protected Map<String, String> headers;
    /**
     * Get the headers included on the response.
     * @return as String, the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    private String _headers = null;
    /**
     * @deprecated Replaced with {@link ApiV2Response#getHeaders()}
     *
     * Get the headers included on the response.
     * @return as String, the headers
     */
    @Deprecated
    public String get_headers() {
        return this._headers;
    }

    /**
     * @deprecated To be removed
     *
     * Set the headers included on the response.
     * @param _headers
     */
    @Deprecated
    public void set_headers(String _headers) {
        this._headers = _headers;
    }

    /**
     * @return
     */
    public boolean success() {
        return (this.getStatus() >= 200 && this.getStatus() < 300);
    }
    public Boolean getSuccess() {
        return this.success();
    }

    /**
     * Returns true if status code is in the 400's, indicating a routing or client error
     * @return
     */
    public boolean clientError() {
        return (this.getStatus() >= 400 && this.getStatus() < 500);
    }
    public Boolean getClientError() {
        return this.clientError();
    }

    /**
     * Returns true if status code is >= 500, indicating a server error
     * @return
     */
    public boolean serverError() {
        return (this.getStatus() >= 500);
    }
    public Boolean getServerError() {
        return this.serverError();
    }

    /**
     * Returns true of there is a clientError, serverError, or status code is < 0
     * @return
     */
    public boolean error() {
        return (this.clientError() || this.serverError() || this.getStatus() < 0);
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getSuccess()}
     *
     * Check whether response status is a success (status code 2xx)
     * @return as Boolean
     */
    @Deprecated
    public Boolean get_success() {
        return this.success();
    }

    /**
     * @deprecated To be removed
     *
     * Set response status
     * @param _success, as Boolean
     */
    @Deprecated
    public void set_success(Boolean _success) {
        // Do nothing.
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getClientError()}
     *
     * Check whether response status is one of 4xx
     * @return as Boolean
     */
    @Deprecated
    public Boolean get_clientError() {
        return this.clientError();
    }

    /**
     * @deprecated To be removed
     *
     * Set response status is one of 4xx
     * @param _clientError ,as Boolean
     */
    @Deprecated
    public void set_clientError(Boolean _clientError) {
        // Do nothing.
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getServerError()}
     *
     * Check whether response status is one of 5xx
     * @return as Boolean
     */
    @Deprecated
    public Boolean get_serverError() {
        return this.serverError();
    }

    /**
     * @deprecated To be removed
     *
     * Set response status is one of 5xx
     * @param _serverError as Boolean
     */
    @Deprecated
    public void set_serverError(Boolean _serverError) {
        // Do nothing.
    }

    /**
     * @deprecated Replaced with {@link ApiV2Response#getError()}
     *
     * Check whether client_error or server_error
     * @return as Boolean
     */
    @Deprecated
    public Boolean get_error() {
        return this.error();
    }

    /**
     * @deprecated To be removed
     *
     * Set whether client_error or server_error
     * @param _error, as Boolean
     */
    @Deprecated
    public void set_error(Boolean _error) {
        // Do nothing.
    }



    private void parseRawBody() {
        try {
            if (StringUtils.hasText(this.rawBody)) {
                this.jsonBody = new JSONObject(this.rawBody);

                if (jsonBody.has("description")) {
                    this.description = jsonBody.getString("description");
                }
                if (jsonBody.has("message")) {
                    this.message = jsonBody.getString("message");
                }
                if (jsonBody.has("errors")) {
                    try {
                        errors = new HashMap<>();
                        JSONObject errorsJson = jsonBody.getJSONObject("errors");
                        Iterator<String> itrErrorKeys = errorsJson.keys();
                        while (itrErrorKeys.hasNext()) {
                            String errorKey = itrErrorKeys.next();
                            errors.put(errorKey, errorsJson.getString(errorKey));
                        }
                    } catch (JSONException je1) {
                        // Do nothing
                    }
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

}
