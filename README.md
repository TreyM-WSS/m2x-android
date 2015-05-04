# AT&T's M2X Android Client

[AT&T M2X](http://m2x.att.com) is a cloud-based fully managed time-series data storage service for network connected machine-to-machine (M2M) devices and the Internet of Things (IoT). 

The [AT&T M2X API](https://m2x.att.com/developer/documentation/overview) provides all the needed operations and methods to connect your devices to AT&T's M2X service. This library aims to provide a simple wrapper to interact with the AT&T M2X API. Refer to the [Glossary of Terms](https://m2x.att.com/developer/documentation/glossary) to understand the nomenclature used throughout this documentation.


Getting Started
==========================
1. Signup for an [M2X Account](https://m2x.att.com/signup).
2. Obtain your _Master Key_ from the [Master Keys](https://m2x.att.com/account#master-keys) tab of your M2X Account Settings.
3. Create your first [Device](https://m2x.att.com/devices) and copy its _Device ID_.
4. Review the [M2X API Documentation](https://m2x.att.com/developer/documentation/overview).


## Setup

Add network permissions to your projects `AndroidManifest.xml` file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Via Maven Central Repository (Recommended)

We've added the M2X Android Client Library to the [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%20com.att.m2x%20a%3A%20android) to make it easy for you to add it as a dependency to your Android based project.

To include the M2X Android Client Library in your project :

1. Add Maven Central Repository to your projects top level `build.gradle` :
    ```
        buildscript {
            ...
            repositories {
                ...
                mavenCentral()
                ...
            }
            ...
       }
    ```

2. Add M2X Android Library as a dependency in module level `build.gradle` :
    ```
    dependencies {
        ...
        compile group: 'com.att.m2x', name: 'android', version: '2.1.1'
        ...
    }
    ```

### Manual Installation

1. Obtain the `m2x-android-v.v.v.aar` for the [latest version](https://github.com/attm2x/m2x-android/releases/latest) of the M2X Android Client Library and place it in your project's `/libs` directory (if no `/libs` directory is present, create it).
2. Add the following to your projects top level `build.gradle` :
    ```
      repositories {
          flatDir {
              dirs 'libs'
          }
      }
    
      dependencies {
          compile fileTree(dir: 'libs', include: ['*.jar'])
          compile 'com.att.m2x.android:m2x-android:2.1.1@aar'
          compile 'com.mcxiaoke.volley:library:1.0.9'
          compile 'com.google.code.gson:gson:2.3.1'
      }
    ```

3. Run `gradle build` task

## Requirements and Dependencies

The M2X Android client requires **Java version 1.5 or greater**.

The client has the following library dependencies, though if you followed the Setup instructions from above all dependencies will be included automatically:
* com.mcxiaoke.volley:library:1.0.9
* com.google.code.gson:gson:2.3.1

## Usage

Initialize using the _Master API Key_ in the onCreate method:

```Java
@Override
    public void onCreate() {
        super.onCreate();
        //Initialize communication library
        M2XAPI.initialize(getApplicationContext(),"<YOUR KEY HERE>");
    }
```

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

In order to run this example, you will need a `Device ID`. If you don't have any, access your M2X account, create a new [Device](https://m2x.att.com/devices), and copy the `Device ID`.

This call updates the device location:

```Java
    try {
        String deviceID = "8d24a72339fdefa88492e194b88f2586";
        JSONObject obj = new JSONObject("{ \"name\": \"Storage Room\",\n" +
            "  \"latitude\": -37.9788423562422,\n" +
            "  \"longitude\": -57.5478776916862,\n" +
            "  \"timestamp\": \"" + DateUtils.dateTimeToString(new Date()) + "\",\n" +
            "  \"elevation\": 5 }");
        Device.updateDeviceLocation(getApplicationContext(), obj, deviceID, new ResponseListener() {
            @Override
            public void onRequestCompleted(ApiV2Response apiV2Response, int i) {
                // Handle Success
            }

            @Override
            public void onRequestError(ApiV2Response apiV2Response, int i) {
                // Handle Error
            }
        });
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
