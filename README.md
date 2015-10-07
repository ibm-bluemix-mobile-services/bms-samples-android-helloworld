# HelloWorld application for IBM Bluemix Mobile Services
---
The HelloWorld sample contains an Android project that you can use to learn and build upon.  
### Downloading the samples and acquiring Android Development Tools
Clone the sample from IBM DevOps Services with the following command:

```bash
git clone https://github.com/ibm-bluemix-mobile-services/bms-samples-android-helloworld
```

If you have not done so already, at this time please acquire and install [Android Studio](https://developer.android.com/sdk/index.html) as well as [Gradle](http://gradle.org/).


### Configure the front end in the HelloWorld sample
1. Using Android Studio, navigate to the bms-samples-android-helloworld directory where the project was cloned.
2. If this is your first time importing a project into Android Studio you will be prompted to define a GRADLE HOME path variable. Set that path to the directory extracted from the Gradle .zip file where the 'bin' directory lives. The 'build.gradle' file will automatically build your project, pulling in the required dependencies.
3. After Gradle has completed synching, open MainActivity.java and locate the try block within the ```onCreate()``` function.
4. In the ```BMSClient.getInstance().initialize()``` function replace ```<APPLICATION_ROUTE>``` and ```<APPLICATION_ID>``` with the application route and ID you were given when creating your application on Bluemix.
```java
		try {
            //initialize SDK with IBM Bluemix application ID and route
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "<APPLICATION_ROUTE>", "<APPLICATION_ID>");
        }
```

### Run the Android App
Now you can run your Android Application in your mobile emulator or on your device.

You will see a single view application with a "PING BLUEMIX" button. When you click this button the application will test the connection from the client to the backend Bluemix application. The application uses the ApplicationRoute specified in the AppDelegate in order to test the connection. The application will then display if the connection was successful or unsuccessful. In the unsuccessful state an error will be displayed in the log as well as in the application.

Note: Inside the ViewController a Get request is made to a protected resource on the Node.js runtime on Bluemix. This code has been provided in the MobileFirst Services Starter boilerplate. If the backend application was not created using the MobileFirst Services Starter boilerplate the application will not be able to connect successfully.


###Supported Levels
The sample is supported on Android API level 17 and up.


### License
Copyright 2015 IBM Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
