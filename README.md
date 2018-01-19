# AT&T's M2X Android Client

[AT&T M2X](http://m2x.att.com) is a cloud-based fully managed time-series data storage service for network connected machine-to-machine (M2M) devices and the Internet of Things (IoT). 

The [AT&T M2X API](https://m2x.att.com/developer/documentation/overview) provides all the needed operations and methods to connect your devices to AT&T's M2X service. This library aims to provide a simple wrapper to interact with the AT&T M2X API. Refer to the [Glossary of Terms](https://m2x.att.com/developer/documentation/glossary) to understand the nomenclature used throughout this documentation.

This library supports Android for mobile development as well as [Android Things](https://developer.android.com/things/index.html) for IoT devices.


Getting Started
==========================
1. Signup for an [M2X Account](https://m2x.att.com/signup).
2. Obtain your _Master Key_ from the [Master Keys](https://m2x.att.com/account#master-keys) tab of your M2X Account Settings.
3. Create your first [Device](https://m2x.att.com/devices) and copy its _Device ID_.
4. Review the [M2X API Documentation](https://m2x.att.com/developer/documentation/overview).

## Setup

### Add Necessary Permissions

Whether you install the library via [Maven](#via-maven-central-repository-recommended) or [manually](#manual-installation), you'll need to add network permissions to your project's `AndroidManifest.xml` file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```
### Install the M2X Android Client Library

There are two options for installing the M2X Android Client Library, seen below. We recommend using the first option, via Maven Centra Repository as it is the most straightforward.

#### Via Maven Central Repository (Recommended)

We've added the M2X Android Client Library to the [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%20com.att.m2x%20a%3A%20android) to make it easy for you to add it as a dependency to your Android based project.

To include the M2X Android Client Library in your project :

1. Add Maven Central Repository to your projects top level `build.gradle` :
    ```
    buildscript {
        repositories {
            mavenCentral()
        }
    }
    ```

2. Add M2X Android Library as a dependency in `/app/build.gradle`, replacing "v.v.v" with the target version number for the M2X Android library (check out our [releases](https://github.com/attm2x/m2x-android/releases) for a full list) :
    ```
    dependencies {
        compile group: 'com.att.m2x', name: 'android', version: 'v.v.v'
    }
    ```

#### Manual Installation

1. Obtain the `m2x-android-v.v.v.aar` for the [latest version](https://github.com/attm2x/m2x-android/releases/latest) of the M2X Android Client Library and place it in your project's `/libs` directory (if no `/libs` directory is present, create it).
2. Add the following to `/app/build.gradle`, replacing "v.v.v" with the target version number for the M2X Android library :
    ```
      repositories {
          flatDir {
              dirs 'libs'
          }
      }
    
      dependencies {
          compile fileTree(dir: 'libs', include: ['*.jar'])
          compile 'com.att.m2x.android:m2x-android:v.v.v@aar'
          compile 'com.mcxiaoke.volley:library:1.0.9'
          compile 'com.google.code.gson:gson:2.3.1'
      }
    ```

3. Run `gradle build` task

### Optional: Android Things Setup

If you are developing an [Android Things](https://developer.android.com/things/index.html) project using one of the [supported hardware kits](https://developer.android.com/things/hardware/developer-kits.html), you will need to follow the Android Things project setup steps found [here](https://developer.android.com/things/training/first-device/create-studio-project.html), which are outlined below. 

_Note: these steps should be performed in addition to the M2X Android client [setup](#setup) instructions from above._

1. Update your SDK tools to version 24 or higher
2. Update your SDK with Android 7.0 (API 24) or higher 
3. Create or update your app project with the following:
  * Add the dependency artifact to your app-level build.gradle file:
    ```
    dependencies {
        ...
        provided 'com.google.android.things:androidthings:0.1-devpreview'
    }
    ```
    
  * Add the things shared library entry to your app's manifest file:
    ```xml
    <application ...>
        <uses-library android:name="com.google.android.things"/>
        ...
    </application>
    ```

  * Declare a home activity as follows:
    ```xml
    <application
        android:label="@string/app_name">
        <uses-library android:name="com.google.android.things"/>
        <activity android:name=".HomeActivity">
            <!-- Launch activity as default from Android Studio -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>

            <!-- Launch activity automatically on boot -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.IOT_LAUNCHER"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </activity>
    </application>
    ```

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
    DistributionService.listDistributions(Activity.this, new TypedResponseListener<List<Distribution>>() {
        @Override
        public void onRequestCompleted(List<Distribution> result) {

        }

        @Override
        public void onRequestError(Response response) {

        }

        @Override
        public void onRequestException(M2XApiException exception) {

        }
    });
   ```

- [Device]
  ```Java
    PublicCatalogSearch search = new PublicCatalogSearch();
    search.setName("device name");
    search.setSort(PublicCatalogSearch.DeviceSort.CREATED);
    search.setSortDirection(SortDirection.ASC);

    DeviceService.searchPublicCatalog(Activity.this, search, new TypedResponseListener<List<Device>>() {
        @Override
        public void onRequestCompleted(List<Device> result) {

        }

        @Override
        public void onRequestError(Response response) {

        }

        @Override
        public void onRequestException(M2XApiException exception) {

        }
    });
   ```

- [Keys]
  ```Java
    // Optionally, use one of:
    // options.setCollectionId(String)
    // options.setDeviceId(String)
    // options.setDistributionId(String)
    ListKeyOptions options = new ListKeyOptions();

    KeyService.listKeys(Activitiy.this, options, new TypedResponseListener<List<Key>>() {
        @Override
        public void onRequestCompleted(List<Key> result) {

        }

        @Override
        public void onRequestError(Response response) {

        }

        @Override
        public void onRequestException(M2XApiException exception) {

        }
    });
   ```

The SDK will return the response from the API in one of the two methods implemented above, or an Exception if it was unable to make the request. If there is an error the SDK provides a detailed message and status to find out which is the problem that caused the failure.

The SDK also provides the ability of returning the response for the last call. To access this last response you will need to do the following:

```Java
M2XAPI.getLastResponse(Activity.this)
```

The call only needs a context and if a call was made the SDK will return the last call response.


Refer to the documentation on each class for further usage instructions.

## Examples

### Update Device

In order to run this example, you will need a `Device ID`. If you don't have any, access your M2X account, create a new [Device](https://m2x.att.com/devices), and copy the `Device ID`.

This call updates the device location:

```Java
    Device device = new Device("<Device ID>");
    
    Location location = new Location();
    location.setName("Storage Room");
    location.setElevation(5.0);
    location.setLatitude(-37.9788423562422);
    location.setLongitude(-57.5478776916862);
    location.setTimestamp(new Date());

    DeviceService.updateDeviceLocation(this, device, location, new TypedResponseListener<Void>() {
        @Override
        public void onRequestCompleted(Void result) {
            // Handle Success
        }

        @Override
        public void onRequestError(Response response) {
            // Handle API Error Response
        }

        @Override
        public void onRequestException(M2XApiException exception) {
            // Handle Exception
        }
    });
```

### Search Devices

The [Search Devices](https://m2x.att.com/developer/documentation/v2/device#Search-Devices) endpoint
accepts a variety of search filters, such as `ids` or `tags`, via the URL query string. However,
some more complex search filters must be requested via a JSON body, such as `streams` and `metadata`.

The SDK provides a typed interface for building search requests:

```Java
    DeviceSearch search = new DeviceSearch();
    search.setLimit(20);
    search.setName("Some Device");
    search.setTags(Collections.singleton("tag"));
    search.setMetadata(Collections.singletonMap("meta_key", "meta_value"));
    
    DeviceService.searchDevices(this, search, new TypedResponseListener<List<Device>>() {
        @Override
        public void onRequestCompleted(List<Device> result) {
            // Handle Success
        }

        @Override
        public void onRequestError(Response response) {
            // Handle API Error Response
        }

        @Override
        public void onRequestException(M2XApiException exception) {
            // Handle Exception
        }
    });
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
