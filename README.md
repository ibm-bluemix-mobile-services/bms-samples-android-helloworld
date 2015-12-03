# Android helloWorld application for Bluemix Mobile Services
---
This helloWorld sample contains an Android project that you can use to learn more about Bluemix Mobile Services.

Use the following steps to configure the helloWorld sample for Android:

1. [Download the helloWorld sample](#download-the-helloworld-sample)
2. [Configure the mobile backend for your helloWorld application](#configure-the-mobile-backend-for-your-helloworld-application)
3. [Configure the front end in the helloWorld sample](#configure-the-front-end-in-the-helloworld-sample)
4. [Run the Android app](#run-the-android-app)
 
### Before you begin
Before you start, make sure you have the following:

- A [Bluemix](http://bluemix.net) account.
  
### Download the helloWorld sample
Clone the sample from IBM DevOps Services with the following command:

```bash
git clone https://github.com/ibm-bluemix-mobile-services/bms-samples-android-helloworld
```

If you have not done so already, at this time please acquire and install [Android Studio](https://developer.android.com/sdk/index.html).

### Configure the mobile backend for your helloWorld application
Before you can run the helloWorld application, you must set up an app on Bluemix.  The following procedure shows you how to create a MobileFirst Services Starter application. A Node.js runtime environment is created so that you can provide server-side functions, such as resource URIs and static files. The Cloudant®NoSQL DB, IBM Push Notifications, and Mobile Client Access services are then added to the app.

Create a mobile backend in the  Bluemix dashboard:

1.	In the **Boilerplates** section of the Bluemix catalog, click **MobileFirst Services Starter**.
2.	Enter a name and host for your mobile backend and click **Create**.
3.	Click **Finish**.

### Configure the front end in the HelloWorld sample
1. Using Android Studio, open the `bms-samples-android-helloworld` directory where the project was cloned.
2. Run a Gradle sync (usually starts automatically) to import the required `core` SDK. You can view the **build.gradle** file in the following directory:

	`app\build.gradle`
	
3. Once that is complete, open `MainActivity.java` and locate the try block within the ```onCreate()``` function.
4. In the ```BMSClient.getInstance().initialize()``` function replace ```<APPLICATION_ROUTE>``` and ```<APPLICATION_ID>``` with the application route and ID you were given when creating your application on Bluemix.
```java
		try {
            //initialize SDK with IBM Bluemix application ID and route
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "<APPLICATION_ROUTE>", "<APPLICATION_ID>");
        }
```

>**Note**: This sample depends on 1.+ version of the Core SDK. This means that the most recent 1.* version will be downloaded automatically. When creating a production applications it is recommended to define the version explicitly (1.0.0 for example) to ensure consistent builds.

### Run the Android App
Now you can run your Android Application in your mobile emulator or on your device.

You will see a single view application with a "PING BLUEMIX" button. When you click this button the application will test the connection from the client to the backend Bluemix application. The application uses the ApplicationRoute specified in the AppDelegate in order to test the connection. The application will then display if the connection was successful or unsuccessful. In the unsuccessful state an error will be displayed in the log as well as in the application.

>**Note**: Inside the **Main Activity** a Get request is made to the Node.js runtime on Bluemix. This code has been provided in the MobileFirst Services Starter boilerplate. If the backend application was not created using the MobileFirst Services Starter boilerplate the application will not be able to connect successfully.


###Supported Levels
The sample is supported on Android API level 15 and up.


### License
This package contains sample code provided in source code form. The samples are licensed under the under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 and may also view the license in the license.txt file within this package. Also see the notices.txt file within this package for additional notices.
