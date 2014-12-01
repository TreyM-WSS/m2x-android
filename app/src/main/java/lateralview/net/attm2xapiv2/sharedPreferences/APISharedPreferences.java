package lateralview.net.attm2xapiv2.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Joaquin on 28/11/14.
 */
public class APISharedPreferences {

    private static final String TAG = APISharedPreferences.class.getSimpleName();
    public final static String M2X_SHARED_PREFERENCES = "M2XSharedPreferences";

    public final static String SHARED_PREFERENCES_API_KEY = "M2XApiKey";

    //Singleton
    public static SharedPreferences sharedPreferences = null;

    /**
     * Method to save the Key
     *
     * @param context
     */
    public static void setApiKey(Context context, String apiKey) {
        sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_API_KEY, apiKey);
        editor.commit();
    }

    /**
     * Method to get the Key from shared preferences
     * @param context
     * @return
     */
    public static String getApiKey(Context context) {
        String versionNumber = "0";
        try{
            sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
            if (sharedPreferences != null) {
                versionNumber = sharedPreferences.getString(SHARED_PREFERENCES_API_KEY,"");
            }
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }finally{
            return versionNumber;
        }
    }
}
