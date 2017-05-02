package com.example.norgaard.tankineedanoperator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastNetworkInfo();
            }
        });
    }

    private void toastNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        String message = networkInfo.getSubtypeName() + " " +
                networkInfo.getReason() + " " +
                networkInfo.getExtraInfo() + " " +
                networkInfo.getTypeName();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
