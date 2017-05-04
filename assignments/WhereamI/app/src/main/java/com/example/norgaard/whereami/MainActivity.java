package com.example.norgaard.whereami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.locationTextView)
    private TextView locationTextView;

    @BindView(R.id.startButton)
    private Button startButton;

    @BindView(R.id.stopButton)
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
