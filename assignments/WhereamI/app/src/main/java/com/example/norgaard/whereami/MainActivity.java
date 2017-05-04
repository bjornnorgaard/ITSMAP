package com.example.norgaard.whereami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.locationTextView)
    TextView locationTextView;

    @BindView(R.id.startButton)
    Button startButton;

    @BindView(R.id.stopButton)
    Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startButton)
    public void OnStartClick() {
        Toast.makeText(this, "Start button clicked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.stopButton)
    public void OnStopClick() {
        Toast.makeText(this, "Stop button clicked!", Toast.LENGTH_SHORT).show();
    }
}
