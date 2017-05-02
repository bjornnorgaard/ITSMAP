package com.example.norgaard.jsonbourne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        
    }
}
