package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

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
public class Command {

    public static final int REQUEST_CODE_LIST_COMMANDS = 5001;
    public static final int REQUEST_CODE_SEND_COMMAND = 5002;
    public static final int REQUEST_CODE_VIEW_COMMAND_DETAILS = 5003;

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void listCommands(Context context, HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COMMANDS_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COMMANDS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Send-Command">Send Command</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void sendCommand(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.COMMANDS_SEND,
                body,
                listener,
                REQUEST_CODE_SEND_COMMAND
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#View-Command-Details">View Command Details</a>} endpoint
     * @param context The application Context.
     * @param commandId as String
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void viewCommandDetails(Context context, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COMMANDS_VIEW_DETAILS, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }

}
