package com.ibm.bms.samples.android.hellobluemix;
/**
 * Copyright 2016 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

/**
 * Main Activity implements Response listener for http request call back handling.
 */
public class MainActivity extends Activity implements ResponseListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView buttonText = (TextView) findViewById(R.id.button_text);

        try {
            //initialize SDK with IBM Bluemix application ID and route
            // You can find your backendRoute and backendGUID in the Mobile Options section on top of your Bluemix MCA dashboard
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "<APPLICATION_ROUTE>", "<APPLICATION_ID>", BMSClient.REGION_US_SOUTH);
        }
        catch (MalformedURLException mue) {
            this.setStatus("Unable to parse Application Route URL\n Please verify you have entered your Application Route and Id correctly and rebuild the app", false);
            buttonText.setClickable(false);
        }
    }

    /**
     * Called when ping bluemix button is pressed.
     * Attempt to access Bluemix backend to ensure proper set up.
     * @param view the button pressed.
     */
    public void pingBluemix(View view) {

        // Get ping Bluemix button and limit any further clicks until response is received.
        TextView buttonText = (TextView) view;
        buttonText.setClickable(false);

        // Update response text while the response is being processed
        TextView responseText = (TextView) findViewById(R.id.response_text);
        responseText.setText("Attempting to Connect");

        Log.i(TAG, "Attempting to Connect");

        // Testing the connection to Bluemix by sending a Get request to the Node.js application. This Node.js code was provided in the MobileFirst Services Starter boilerplate.
        // The below 'Request' uses the core sdk to send the REST request using the applicationRoute that was provided when initializing the BMSClient earlier.
        // Since this MainActivity class implements the ResponseListener interface, we can pass 'this' as one of the send() parameters to handle the response.
        new Request(BMSClient.getInstance().getBluemixAppRoute(), Request.GET).send(this, this);
    }

    // Success handler called when successful response from Bluemix is received
    @Override
    public void onSuccess(Response response) {
        setStatus("", true);
        Log.i(TAG, "Successfully pinged Bluemix!");
    }

    // Failure handler called when an unsuccessful response from Bluemix is received
    @Override
    public void onFailure(Response response, Throwable throwable, JSONObject extendedInfo) {
        String errorMessage = "";

        if (response != null) {
            if (response.getStatus() == 404) {
                errorMessage += "Application Route not found at:\n" + BMSClient.getInstance().getBluemixAppRoute() +
                        "\nPlease verify your Application Route and rebuild the app.";
            } else {
                errorMessage += response.toString() + "\n";
            }
        }

        if (throwable != null) {
            if (throwable.getClass().equals(UnknownHostException.class)) {
                errorMessage = "Unable to access Bluemix host!\nPlease verify internet connectivity and try again.";
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                errorMessage += "THROWN" + sw.toString() + "\n";
            }
        }

        if (extendedInfo != null){
            errorMessage += "EXTENDED_INFO" + extendedInfo.toString() + "\n";
        }

        if (errorMessage.isEmpty())
            errorMessage = "Request Failed With Unknown Error.";

        setStatus(errorMessage, false);
        Log.e(TAG, "Get request to Bluemix failed: " + errorMessage);
    }

	/**
     * Updates text fields in the UI
     * @param errorText error String that displays in center text box
     * @param wasSuccessful Boolean that decides appropriate success vs failure text to display
     */
    private void setStatus(final String errorText, boolean wasSuccessful){
        final TextView responseText = (TextView) findViewById(R.id.response_text);
        final TextView topText = (TextView) findViewById(R.id.top_text);
        final TextView bottomText = (TextView) findViewById(R.id.bottom_text);
        final TextView buttonText = (TextView) findViewById(R.id.button_text);
        final String topStatus = wasSuccessful ? "Yay!" : "Bummer";
        final String bottomStatus = wasSuccessful ? "You Are Connected" : "Something Went Wrong";

        // Since this is being done on a separate thread (within success or failure response) we need to run this update on the UI thread or else the app will crash.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonText.setClickable(true);
                responseText.setText(errorText);
                topText.setText(topStatus);
                bottomText.setText(bottomStatus);
            }
        });
    }

}
