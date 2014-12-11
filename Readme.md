Android M2X API Client
=====================

The AT&T [M2X API](https://m2x.att.com/developer/documentation/v2/overview) provides all the needed operations and methods to connect your devices to AT&T's M2X service. This client provides an easy to use interface for your favorite mobile platform, [Android](http://developer.android.com/index.html).


Getting Started
------------
1. Signup for an M2X Account: https://m2x.att.com/signup
2. Obtain your *Master Key* from the Master Keys tab of your Account Settings: https://m2x.att.com/account
3. Review the M2X API Documentation: https://m2x.att.com/developer/documentation/v2/overview

If you have questions about any M2X specific terms, please consult the M2X glossary: https://m2x.att.com/developer/documentation/v2/glossary

Before using this SDK
------

Please use caution when using it in a production environment. Classes and method signatures are all subject to change.

Using the Android SDK
------------

To start using this SDK you will need to follow the steps below:

1. Add network permissions in your manifest file

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

2. Get the .aar file from the project root and import it in android studio as a new module (File->New Module->From .AAR).
3. Open your project settings and create a dependency between your app and the android sdk module.
4. Your project should now compile. To start using M2X, set up your [Master Key](https://m2x.att.com/account#master-keys-tab). The Master Key is required for some API features such as the ability to retrieve the full list of your feeds. 

You need to extend the application class and set the [Master Key] in the onCreate method.

```Java
@Override
    public void onCreate() {
        super.onCreate();
        //Initialize communication library
        M2XAPI.initialize(getApplicationContext(),"84c57939b4ab16a294aecae21b9dd72a");
    }
```

API Client Examples
-----

All the methods available are explained in the AT&T [M2X API](https://m2x.att.com/developer/documentation/v2/overview) which you can check to see the parameters that need to be provided to each method.

For example if you want to list all the distributions use the following:

```Java
 Distribution.list(DistributionActivity.this, new ResponseListener() {
    @Override
    public void onRequestCompleted(ApiV2Response result, int requestCode) {
        
    }

    @Override
    public void onRequestError(ApiV2Response error, int requestCode) {

    }
});
 ```

 The SDK will return the response from the API in one of the two methods implmented above. If there is an error the SDK provides a detailed message and status to find out which is the problem that caused the failure.

 The SDK also provides the ability of returning the response for the last call. To access this last response you will need to do the following:

 ```Java
 M2XAPI.getLastResponse(Activity.this)
 ```

 The call only needs a context and if a call was made the SDK will return the last call response.

## License

The Android M2X API Client is available under the MIT license. See the [LICENSE](LICENSE) file for more information.
