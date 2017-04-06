package com.example.norgaard.selfieswap;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private static final String SWAPPED_KEY = "asifdhsiudfkisduhfkiu";
    String debugTag = "Swap";
    boolean isSwapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isSwapped = savedInstanceState.getBoolean(SWAPPED_KEY);
            Log.d(debugTag, "onCreate(): isSwapped is : " + isSwapped);
        }

        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.swapButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSwapButton();
            }
        });
    }

    public void onClickSwapButton() {
        if (isSwapped == true) {
            isSwapped = false;
        } else {
            isSwapped = true;
        }
        Log.d(debugTag, "User clicked swap button, " + isSwapped);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(SWAPPED_KEY, isSwapped);

        super.onSaveInstanceState(outState);
    }
}
