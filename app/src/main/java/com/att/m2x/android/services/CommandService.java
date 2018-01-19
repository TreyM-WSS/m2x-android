package com.att.m2x.android.services;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Command;
import com.att.m2x.android.services.model.CommandSummary;
import com.att.m2x.android.services.model.ListCommandsOptions;
import com.att.m2x.android.services.model.SendCommand;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.BaseResponseListener.EmptyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Commands are used to communicate with one or more specific devices. Commands will be delivered
 * directly to target devices that are connected via MQTT, or devices may check periodically for
 * outstanding commands via HTTP.
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/commands">M2X Commands REST API</a>}
 */
public class CommandService {

    public static final int REQUEST_CODE_LIST_COMMANDS = 5001;
    public static final int REQUEST_CODE_SEND_COMMAND = 5002;
    public static final int REQUEST_CODE_VIEW_COMMAND_DETAILS = 5003;

    private CommandService() {
        // don't instantiate
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a>} endpoint
     * @param context
     * @param listCommandsOptions
     * @param listener
     */
    public static void listCommands(Context context, ListCommandsOptions listCommandsOptions,
                                    TypedResponseListener<List<CommandSummary>> listener) {
        listCommands(context, listCommandsOptions.toMap(), new CommandListResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a> endpoint
     * @param context
     * @param params
     * @param listener
     */
    public static void listCommands(Context context, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COMMANDS_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COMMANDS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a> endpoint
     * @param context
     * @param command
     * @param listener
     */
    public static void sendCommand(Context context, SendCommand command,
                                   TypedResponseListener<Void> listener) {
        sendCommand(context, command.toJsonObject(), new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">List Sent Commands</a> endpoint
     * @param context
     * @param body
     * @param listener
     */
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
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">View Command Details</a> endpoint
     * @param context
     * @param commandId
     * @param listener
     */
    public static void viewCommandDetails(Context context, String commandId,
                                          TypedResponseListener<Command> listener) {
        viewCommandDetails(context, commandId, new CommandResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#List-Sent-Commands">View Command Details</a> endpoint
     * @param context
     * @param commandId
     * @param listener
     */
    public static void viewCommandDetails(Context context, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COMMANDS_VIEW_DETAILS, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// RESPONSE LISTENERS
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class CommandResponseListener extends BaseResponseListener<Command> {
        CommandResponseListener(TypedResponseListener<Command> listener) {
            super(listener);
        }

        @Override
        Command parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return Command.fromJsonObject(jsonObject);
        }
    }

    private static class CommandListResponseListener extends BaseResponseListener<List<CommandSummary>> {
        CommandListResponseListener(TypedResponseListener<List<CommandSummary>> listener) {
            super(listener);
        }

        @Override
        List<CommandSummary> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            List<CommandSummary> commandList = new ArrayList<>();
            if (jsonObject != null && !jsonObject.isNull("commands")) {
                JSONArray commandArray = jsonObject.getJSONArray("commands");
                for (int i = 0; i < commandArray.length(); ++i) {
                    commandList.add(CommandSummary.fromJsonObject(commandArray.getJSONObject(i)));
                }
            }
            return commandList;
        }
    }
}
