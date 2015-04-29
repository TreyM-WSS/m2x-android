# AT&T's M2X Android Client

[AT&T M2X](http://m2x.att.com) is a cloud-based fully managed time-series data storage service for network connected machine-to-machine (M2M) devices and the Internet of Things (IoT). 

The [AT&T M2X API](https://m2x.att.com/developer/documentation/overview) provides all the needed operations and methods to connect your devices to AT&T's M2X service. This library aims to provide a simple wrapper to interact with the AT&T M2X API. Refer to the [Glossary of Terms](https://m2x.att.com/developer/documentation/glossary) to understand the nomenclature used throughout this documentation.


Getting Started
==========================
1. Signup for an [M2X Account](https://m2x.att.com/signup).
2. Obtain your _Master Key_ from the Master Keys tab of your [Account Settings](https://m2x.att.com/account) screen.
2. Create your first [Device](https://m2x.att.com/devices) and copy its _Device ID_.
3. Review the [M2X API Documentation](https://m2x.att.com/developer/documentation/overview).

## Setup

To start using this SDK you will need to follow the steps below:

1. Add network permissions in your manifest file

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

2. Generate an .aar file or import this project from the Android Studio import project wizard.
3. Open your project settings and create a dependency between your app and the android module or library.
4. Your project should now compile. To start using M2X, obtain your [Master Key](https://m2x.att.com/account#master-keys). The Master Key is required for some API features such as the ability to retrieve the full list of your devices.

You need to extend the application class and set the [Master Key] in the onCreate method.

```Java
@Override
    public void onCreate() {
        super.onCreate();
        //Initialize communication library
        M2XAPI.initialize(getApplicationContext(),"<YOUR KEY HERE>");
    }
```

## Usage

All the methods available are explained in the AT&T [M2X API](https://m2x.att.com/developer/documentation/overview) which you can check to see the parameters that need to be provided to each method.


This provides an interface to your data in M2X

- [Distribution]
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

- [Device]
  ```Java
    public void searchPublicDevicesCatalog(View view) {
          HashMap<String,String> params = new HashMap<String, String>();
          params.put("p","1");
          Device.searchPublicCatalog(DeviceActivity.this, params,
            new ResponseListener() {
                @Override
                public void onRequestCompleted(ApiV2Response result, int requestCode) {

                }

                @Override
                public void onRequestError(ApiV2Response error, int requestCode) {

                }
            });
    }
   ```

- [Keys]
  ```Java
        Key.list(KeysActivity.this,null,
         new ResponseListener() {
              @Override
              public void onRequestCompleted(ApiV2Response result, int requestCode) {

              }

              @Override
              public void onRequestError(ApiV2Response error, int requestCode) {

              }
          });
   ```

- [Charts]
  ```Java
    Charts.list(ChartsActivity.this,null,
        new ResponseListener() {
           @Override
           public void onRequestCompleted(ApiV2Response result, int requestCode) {

           }

           @Override
           public void onRequestError(ApiV2Response error, int requestCode) {

           }
       });
   ```

The SDK will return the response from the API in one of the two methods implemented above. If there is an error the SDK provides a detailed message and status to find out which is the problem that caused the failure.

The SDK also provides the ability of returning the response for the last call. To access this last response you will need to do the following:

```Java
M2XAPI.getLastResponse(Activity.this)
```

The call only needs a context and if a call was made the SDK will return the last call response.


Refer to the documentation on each class for further usage instructions.

## Example

In order to run this example, you will need a `Device ID` and `API Key`. If you don't have any, access your M2X account, create a new [Device](https://m2x.att.com/devices), and copy the `Device ID` and `API Key` values.

This call updates the device location:

```Java
    try {
        JSONObject obj = new JSONObject("{ \"name\": \"Storage Room\",\n" +
                "  \"latitude\": -37.9788423562422,\n" +
                "  \"longitude\": -57.5478776916862,\n" +
                "  \"timestamp\": \"2014-09-10T19:15:00.756Z\",\n" +
                "  \"elevation\": 5 }");
        Device.updateDeviceLocation(DeviceActivity.this,obj,"437cb907799ae7e01309133006806ba3",this);
    } catch (JSONException e) {
        e.printStackTrace();
    }
```

## Versioning

This SDK aims to adhere to [Semantic Versioning 2.0.0](http://semver.org/). As a summary, given a version number `MAJOR.MINOR.PATCH`:

1. `MAJOR` will increment when backwards-incompatible changes are introduced to the client.
2. `MINOR` will increment when backwards-compatible functionality is added.
3. `PATCH` will increment with backwards-compatible bug fixes.

Additional labels for pre-release and build metadata are available as extensions to the `MAJOR.MINOR.PATCH` format.

**Note**: the client version does not necessarily reflect the version used in the AT&T M2X API.


## Test/Sample Android Application
To get you up and running quickly, we have provided a sample/test Android application for you here: https://github.com/attm2x/m2x-android-test-app

## License

This SDK is provided under the MIT license. See [LICENSE](LICENSE) for applicable terms.
