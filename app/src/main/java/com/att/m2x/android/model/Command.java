package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.model.ListCommandsOptions;
import com.att.m2x.android.services.model.SendCommand;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Commands are used to communicate with one or more specific devices. Commands will be delivered directly to target devices that are connected via MQTT,
 * or devices may check periodically for outstanding commands via HTTP.
 * The Commands API allows you to send custom user-defined commands to devices and allows devices to issue their responses.
 * The Commands API can also be used to view and audit the history of recently sent commands, along with the device responses.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Command implements IModelObject {


    private String id;
    private String url;
    private String name;
    private Map<String, String> data;
    private Date sentAt;
    private Map<String, Delivery> deliveries;

    public static class Delivery implements IModelObject {
        CommandStatus status;
        Date receivedAt;
        Map<String, String> responseData;

        public Delivery() {
        }

        public Delivery(CommandStatus status, Date receivedAt, Map<String, String> responseData) {
            this.status = status;
            this.receivedAt = receivedAt;
            this.responseData = responseData;
        }

        public CommandStatus getStatus() {
            return status;
        }

        public void setStatus(CommandStatus status) {
            this.status = status;
        }

        public Date getReceivedAt() {
            return receivedAt;
        }

        public void setReceivedAt(Date receivedAt) {
            this.receivedAt = receivedAt;
        }

        public Map<String, String> getResponseData() {
            return responseData;
        }

        public void setResponseData(Map<String, String> responseData) {
            this.responseData = responseData;
        }

        @Override
        public JSONObject toJsonObject() {
            JSONObject jsonObject = new JSONObject();
            try {
                if (status != null) jsonObject.put("status", status.value());
                if (receivedAt != null) jsonObject.put("received_at", DateUtils.dateTimeToString(receivedAt));
                if (responseData != null) jsonObject.put("response_data", ArrayUtils.mapToJsonObject(responseData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        public void fromJson(JSONObject jsonObject) throws JSONException {
            if (!jsonObject.isNull("status")) status = CommandStatus.parse(jsonObject.getString("status"));
            if (!jsonObject.isNull("received_at")) try {
                receivedAt = DateUtils.stringToDateTime(jsonObject.getString("received_at"));
            } catch (ParseException e) {
                throw new JSONException(e.getMessage());
            }
            if (!jsonObject.isNull("response_data")) {
                responseData = ArrayUtils.jsonObjectToMap(jsonObject);
            }
        }

        public static Delivery fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            Delivery delivery = new Delivery();
            delivery.fromJson(jsonObject);
            return delivery;
        }
    }

    public enum CommandStatus {
        PENDING("pending"),
        REJECTED("rejected"),
        PROCESSED("processed");

        private String value;

        CommandStatus(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static CommandStatus parse(String status) {
            if (status == null) return null;
            switch (status.toLowerCase()) {
                case "pending": return PENDING;
                case "rejected": return REJECTED;
                case "processed": return PROCESSED;
            }
            throw new IllegalArgumentException("Invalid Command Status: " + status);
        }
    }

    public Command() {
    }

    public Command(String id, String url, String name, Map<String, String> data, Date sentAt, Map<String, Delivery> deliveries) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.data = data;
        this.sentAt = sentAt;
        this.deliveries = deliveries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Map<String, Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Map<String, Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public Delivery getDelivery(String deviceId) {
        if (this.deliveries == null) return null;
        return this.deliveries.get(deviceId);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id != null) jsonObject.put("id", id);
            if (url != null) jsonObject.put("url", url);
            if (name != null) jsonObject.put("name", name);
            if (data != null) jsonObject.put("data", ArrayUtils.mapToJsonObject(data));
            if (sentAt != null) jsonObject.put("sent_at", DateUtils.dateTimeToString(sentAt));
            if (deliveries != null) {
                JSONObject deliveriesObj = new JSONObject();
                for (Map.Entry<String, Delivery> e : deliveries.entrySet()) {
                    deliveriesObj.put(e.getKey(), deliveries.get(e.getKey()).toJsonObject());
                }
                jsonObject.put("deliveries", deliveries);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("id")) id = jsonObject.getString("id");
        if (jsonObject.has("url")) url = jsonObject.getString("url");
        if (jsonObject.has("name")) name = jsonObject.getString("name");
        if (!jsonObject.isNull("data")) data = ArrayUtils.jsonObjectToMap(jsonObject.getJSONObject("data"));
        if (!jsonObject.isNull("sent_at")) try {
            sentAt = DateUtils.stringToDateTime(jsonObject.getString("sent_at"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("deliveries")) {
            JSONObject deliveriesObj = jsonObject.getJSONObject("deliveries");
            deliveries = new HashMap<>();
            for (Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {
                String key = keys.next();
                deliveries.put(key, Delivery.fromJsonObject(jsonObject.getJSONObject(key)));
            }
        }
    }

    public static Command fromJsonObject(JSONObject jsonObject) throws JSONException {
        Command command = new Command();
        command.fromJson(jsonObject);
        return command;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //
    //
    //  DEPRECATED STATIC METHODS ->
    //      MOVED TO CommandService and DeviceService
    //
    //
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public static final int REQUEST_CODE_LIST_COMMANDS = 5001;
    public static final int REQUEST_CODE_SEND_COMMAND = 5002;
    public static final int REQUEST_CODE_VIEW_COMMAND_DETAILS = 5003;

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CommandService#listCommands(Context, ListCommandsOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listCommands(Context context, HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COMMANDS_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COMMANDS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CommandService#sendCommand(Context, SendCommand, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Send-Command">Send Command</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void sendCommand(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.COMMANDS_SEND,
                body,
                listener,
                REQUEST_CODE_SEND_COMMAND
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CommandService#viewCommandDetails(Context, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#View-Command-Details">View Command Details</a>} endpoint
     * @param context The application Context.
     * @param commandId as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewCommandDetails(Context context, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COMMANDS_VIEW_DETAILS, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }

}
