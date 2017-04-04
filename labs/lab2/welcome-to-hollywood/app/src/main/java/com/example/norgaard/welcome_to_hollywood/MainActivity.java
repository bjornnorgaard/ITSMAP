package com.example.norgaard.welcome_to_hollywood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "OnRemove");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle", "OnRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", "OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "OnDestroy");
    }
}
