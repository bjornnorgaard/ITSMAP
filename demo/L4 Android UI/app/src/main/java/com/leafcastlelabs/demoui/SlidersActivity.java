package com.leafcastlelabs.demoui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;


public class SlidersActivity extends Activity {

    private RelativeLayout bgSlidersColor;
    private SeekBar skbRed;
    private SeekBar skbGreen;
    private SeekBar skbBlue;
    private Button btnOk;
    private Button btnCancel;

    private int red = 100;
    private int green = 100;
    private int blue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliders);

        bgSlidersColor = (RelativeLayout)findViewById(R.id.bgSlidersColor);
        skbRed = (SeekBar)findViewById(R.id.skbRed);
        skbGreen = (SeekBar)findViewById(R.id.skbGreen);
        skbBlue = (SeekBar)findViewById(R.id.skbBlue);

        skbRed.setProgress(red);
        skbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = progress;
                updateBackgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbGreen.setProgress(green);
        skbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = progress;
                updateBackgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbBlue.setProgress(blue);
        skbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = progress;
                updateBackgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("color", "RGB(" + red + "," + green + "," + blue + ")");
                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        updateBackgroundColor();
    }

    private void updateBackgroundColor(){
        int newColor = Color.rgb(red, green, blue);
        bgSlidersColor.setBackgroundColor(newColor);
    }
}
