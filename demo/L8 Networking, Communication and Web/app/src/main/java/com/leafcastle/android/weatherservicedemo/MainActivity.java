package com.leafcastle.android.weatherservicedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leafcastle.android.weatherservicedemo.model.CityWeather;
import com.leafcastle.android.weatherservicedemo.utils.Globals;
import com.leafcastle.android.weatherservicedemo.utils.NetworkChecker;
import com.leafcastle.android.weatherservicedemo.utils.WeatherJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.leafcastle.android.weatherservicedemo.utils.Globals.CONNECT;

public class MainActivity extends AppCompatActivity {

    /*
     * Network calls made based on code snippets at: http://developer.android.com/training/basics/network-ops/connecting.html
     */

    private Button btnCheckNetwork, btnSendRequest, btnJson, btnSwitch;
    private TextView txtResponse, txtJsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResponse = (TextView) findViewById(R.id.txtResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        txtJsonResponse = (TextView) findViewById(R.id.txtJsonResult);

        btnSendRequest = (Button) findViewById(R.id.btnSendRequest);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
        btnCheckNetwork = (Button) findViewById(R.id.btnCheckNetwork);
        btnCheckNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetwork();
            }
        });

        btnJson = (Button) findViewById(R.id.btnJson);
        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try to convert JSON
                if(txtResponse.getText().toString()!=null){
                    //try to interpret JSON
                    interpretWeatherJSON(txtResponse.getText().toString());
                }
            }
        });

        btnSwitch = (Button) findViewById(R.id.btnSwitchMode);
        btnSwitch.setText("Switch to Volley");
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, VolleyActivity.class);
                startActivity(in);

            }
        });
    }

    private void checkNetwork(){
        String status = NetworkChecker.getNetworkStatus(this);
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
    }

    private void sendRequest(){
        DownloadTask d = new DownloadTask();
        d.execute(Globals.WEATHER_API_CALL);
    }

    //we extend Asynch task and can create any number of these new DownloadTasks as need - need to call execute()
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d(CONNECT, "Starting background task");
            return callURL(urls[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            if(result!=null) {
                txtResponse.setText(result);
            } else {

            }
        }
    }


    private String callURL(String callUrl) {

        InputStream is = null;

        try {
            //create URL
            URL url = new URL(callUrl);

            //configure HttpURLConnetion object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //we could use HttpsURLConnection, weather API does not support SSL on free version
            //HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);



            // Starts the request
            conn.connect();
            int response = conn.getResponseCode();

            //probably check check on response code here!

            //give user feedback in case of error

            Log.d(CONNECT, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string

            String contentAsString = convertStreamToStringBuffered(is);
            return contentAsString;


        } catch (ProtocolException pe) {
            Log.d(CONNECT, "oh noes....ProtocolException");
        } catch (UnsupportedEncodingException uee) {
            Log.d(CONNECT, "oh noes....UnsuportedEncodingException");
        } catch (IOException ioe) {
            Log.d(CONNECT, "oh noes....IOException");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    Log.d(CONNECT, "oh noes....could not close stream, IOException");
                }
            }
        }
        return null;
    }


    private String convertStreamToStringBuffered(InputStream is) {
        String s = "";
        String line = "";

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));


        try {
            while ((line = rd.readLine()) != null) { s += line; }
        } catch (IOException ex) {
            Log.e(CONNECT, "ERROR reading HTTP response", ex);
            //ex.printStackTrace();
        }

        // Return full string
        return s;
    }

    //attempt to decode the json response from weather server
    public void interpretWeatherJSON(String jsonResonse){
        txtJsonResponse.setText(WeatherJsonParser.parseCityWeatherJsonWithGson(jsonResonse));
    }
}
