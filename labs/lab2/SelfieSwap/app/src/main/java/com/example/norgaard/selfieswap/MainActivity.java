package com.example.norgaard.selfieswap;

import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private static final String SWAPPED_KEY = "asifdhsiudfkisduhfkiu";
    String debugTag = "Swap";
    boolean isSwapped;
    ImageView bar;
    ImageView foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isSwapped = savedInstanceState.getBoolean(SWAPPED_KEY);
            Log.d(debugTag, "onCreate(): isSwapped is : " + isSwapped);
        }

        foo = (ImageView) findViewById(R.id.foo);
        bar = (ImageView) findViewById(R.id.bar);

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
        }
        else {
            isSwapped = true;
        }

        foo.setImageResource(R.drawable.bar);
        bar.setImageResource(R.drawable.foo);

        Log.d(debugTag, "User clicked swap button, " + isSwapped);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(SWAPPED_KEY, isSwapped);

        super.onSaveInstanceState(outState);
    }
}
