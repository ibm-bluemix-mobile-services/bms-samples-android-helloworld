package com.ibm.bms.samples.android.helloworld;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class MainActivity extends Activity implements ResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.adjustTypefaceForMainView();
        TextView buttonText = (TextView) findViewById(R.id.button_text);

        try {
            //initialize SDK with IBM Bluemix application ID and route
            //TODO: Please replace <APPLICATION_ROUTE> with a valid ApplicationRoute and <APPLICATION_ID> with a valid ApplicationId
            BMSClient.getInstance().initialize(this, "<APPLICATION_ROUTE>", "<APPLICATION_ID>");
        }
        catch (MalformedURLException mue) {
            this.setStatus("Unable to parse Application Route URL\n Please verify you have entered your Application Route and Id correctly and rebuild the app", false);
            buttonText.setClickable(false);
        }
    }

    public void pingBluemix(View view) {
        final String buttonWaitColor = "#ff00AED3";

        TextView buttonText = (TextView) findViewById(R.id.button_text);
        buttonText.setBackgroundColor(Color.parseColor(buttonWaitColor));
        buttonText.setClickable(false);

        TextView errorText = (TextView) findViewById(R.id.error_text);
        errorText.setText("");

        //Testing the connection to Bluemix by sending a Get request to a protected resource in the Node.js application.
        // This Node.js code was provided in the MobileFirst Services Starter boilerplate.
        // The below request uses the applicationRoute that was provided when initializing the BMSClient in the onCreate.
        new Request(BMSClient.getInstance().getBluemixAppRoute() + "/protected", Request.GET).send(this.getApplicationContext(), this);
    }

    private void adjustTypefaceForMainView () {
        Typeface IBMFont = Typeface.createFromAsset(getAssets(), "fonts/helvetica-neue-light.ttf");

        TextView errorText = (TextView) findViewById(R.id.error_text);
        errorText.setTypeface(IBMFont);
        TextView topText = (TextView) findViewById(R.id.top_text);
        topText.setTypeface(IBMFont);
        TextView bottomText = (TextView) findViewById(R.id.bottom_text);
        bottomText.setTypeface(IBMFont);
        TextView buttonText = (TextView) findViewById(R.id.button_text);
        buttonText.setTypeface(IBMFont);
    }

    private void setStatus(final String messageText, boolean wasSuccessful){
        final TextView errorText = (TextView) findViewById(R.id.error_text);
        final TextView topText = (TextView) findViewById(R.id.top_text);
        final TextView bottomText = (TextView) findViewById(R.id.bottom_text);
        final TextView buttonText = (TextView) findViewById(R.id.button_text);
        final String buttonDefaultColor = "#ff1CB299";
        final String topStatus = wasSuccessful ? "Yay!" : "Bummer";
        final String bottomStatus = wasSuccessful ? "You Are Connected" : "Something Went Wrong";

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonText.setBackgroundColor(Color.parseColor(buttonDefaultColor));
                buttonText.setClickable(true);
                errorText.setText(messageText);
                topText.setText(topStatus);
                bottomText.setText(bottomStatus);
            }
        });
    }

    @Override
    public void onSuccess(Response response) {
        setStatus("", true);
    }

    @Override
    public void onFailure(Response response, Throwable throwable, JSONObject jsonObject) {
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

        if (errorMessage.isEmpty())
            errorMessage = "Authorization Process Failed With Unknown Error.";

        setStatus(errorMessage, false);
    }
}
