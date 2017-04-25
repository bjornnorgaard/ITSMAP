package com.example.norgaard.allyourdatabasearebelongtous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText taskPlaceEditText;
    private EditText taskNameEditText;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskPlaceEditText = (EditText) findViewById(R.id.taskPlaceEditText);
        taskNameEditText = (EditText) findViewById(R.id.taskNameEditText);
        okButton = (Button) findViewById(R.id.okButton);
    }
}
