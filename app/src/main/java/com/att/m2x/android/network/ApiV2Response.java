package com.att.m2x.android.network;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * The Response Class <p>
 */

public class ApiV2Response implements Serializable {


    /**
     * raw: The raw response body.
     */
    @SerializedName("raw")
    private String _raw;
    /**
     * json: The parsed response body.
     */
    @SerializedName("json")
    private JSONObject _json;
    /**
     * status: The status code of the response.
     */
    @SerializedName("status")
    private String _status;
    /**
     * headers: The headers included on the response.
     */
    @SerializedName("headers")
    private String _headers;

    /**
     * success?: Whether response status is a success (status code 2xx)
     */
  	//Generated Fields
    private Boolean _success;
    /**
     * client_error?: Whether response status is one of 4xx
     */
    private Boolean _clientError;
    /**
     *server_error?: Whether response status is one of 5xx
     */
    private Boolean _serverError;
    /**
     *error?: Whether client_error? or server_error? is true
     */
    private Boolean _error;


    /**
     * Gets the raw response body
     * @return as String, the raw response body
     */
    public String get_raw() {
        return _raw;
    }

    /**
     * Sets the raw response body
     * @param _raw as String
     */
    public void set_raw(String _raw) {
        this._raw = _raw;
    }

    /**
     * Gets the parsed response body
     * @return as JSON Object, the parsed response body
     */
    public JSONObject get_json() {
        return _json;
    }

    /**
     * Sets the parsed response body
     * @param _json as JSON Object
     */
    public void set_json(JSONObject _json) {
        this._json = _json;
    }

    /**
     * Get the status code of the response.
     * @return as String, the status code
     */
    public String get_status() {
        return _status;
    }

    /**
     * Set the status code of the response.
     * @param _status, as String
     */
    public void set_status(String _status) {
        this._status = _status;
    }

    /**
     * Get the headers included on the response.
     * @return as String, the headers
     */
    public String get_headers() {
        return _headers;
    }

    /**
     * Set the headers included on the response.
     * @param _headers
     */
    public void set_headers(String _headers) {
        this._headers = _headers;
    }

    /**
     * Check whether response status is a success (status code 2xx)
     * @return as Boolean
     */
    public Boolean get_success() {
        return _success;
    }

    /**
     * Set response status
     * @param _success, as Boolean
     */
    public void set_success(Boolean _success) {
        this._success = _success;
    }

    /**
     * Check whether response status is one of 4xx
     * @return as Boolean
     */
    public Boolean get_clientError() {
        return _clientError;
    }

    /**
     * Set response status is one of 4xx
     * @param _clientError ,as Boolean
     */
    public void set_clientError(Boolean _clientError) {
        this._clientError = _clientError;
    }

    /**
     * Check whether response status is one of 5xx
     * @return as Boolean
     */
    public Boolean get_serverError() {
        return _serverError;
    }

    /**
     * Set response status is one of 5xx
     * @param _serverError as Boolean
     */
    public void set_serverError(Boolean _serverError) {
        this._serverError = _serverError;
    }

    /**
     * Check whether client_error or server_error
     * @return as Boolean
     */
    public Boolean get_error() {
        return _error;
    }

    /**
     * Set whether client_error or server_error
     * @param _error, as Boolean
     */
    public void set_error(Boolean _error) {
        this._error = _error;
    }
}
