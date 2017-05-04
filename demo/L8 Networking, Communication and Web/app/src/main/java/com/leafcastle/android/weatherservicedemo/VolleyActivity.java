package com.leafcastle.android.weatherservicedemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leafcastle.android.weatherservicedemo.utils.Globals;
import com.leafcastle.android.weatherservicedemo.utils.NetworkChecker;
import com.leafcastle.android.weatherservicedemo.utils.WeatherJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    /*
     * Code based on http://developer.android.com/training/volley/simple.html
     */

    private Button btnCheckNetwork, btnSendRequest, btnJson, btnSwitch;
    private TextView txtResponse, txtJsonResponse;

    //for Volley
    RequestQueue queue;

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
                    //TODO: move last result to member variable instead of reading from TextView
                    interpretWeatherJSON(txtResponse.getText().toString());
                }
            }
        });

        btnSwitch = (Button) findViewById(R.id.btnSwitchMode);
        btnSwitch.setText("Switch to HttpURLConnection");
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void checkNetwork(){
        String status = NetworkChecker.getNetworkStatus(this);
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
    }

    private void sendRequest(){
        //send request using Volley
        if(queue==null){
            queue = Volley.newRequestQueue(this);
        }
        String url = Globals.WEATHER_API_CALL;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        txtResponse.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtResponse.setText("That didn't work! ");
            }
        });

        queue.add(stringRequest);

    }


    //attempt to decode the json response from weather server
    public void interpretWeatherJSON(String jsonResonse){

        txtJsonResponse.setText(WeatherJsonParser.parseCityWeatherJsonWithGson(jsonResonse));
    }
}
