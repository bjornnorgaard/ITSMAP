package com.example.norgaard.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void HelloClicked(View view) {
        TextView derp = (TextView) findViewById(R.id.textView);

        String helloAndroidText = "Hello Android";
        String helloWorldText = "Hello World!";
        if (derp.getText() != helloAndroidText) {
            derp.setText(helloAndroidText);
        } else {
            derp.setText(helloWorldText);
        }
    }

    public void ExitClicked(View view) {
        System.exit(1);
    }
}
