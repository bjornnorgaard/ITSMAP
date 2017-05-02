package com.example.norgaard.jsonbourne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView weatherTextView;
    private Button getWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherButton = (Button) findViewById(R.id.getWeatherButton);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);

        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });
    }

    private void getWeather() {
        String urlString = "www.stuff.dk";
        HttpsURLConnection connection = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }

            stream = connection.getInputStream();

            if (stream != null) {
                result = readStream(stream,500);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
