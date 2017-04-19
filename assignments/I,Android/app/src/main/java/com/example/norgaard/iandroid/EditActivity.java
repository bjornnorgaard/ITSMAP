package com.example.norgaard.iandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewId;
    CheckBox checkBoxAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // FindById and stuff

        Intent intent = getIntent();
        textViewName.setText(intent.getStringExtra(getString(R.string.name_field_key)));
        textViewId.setText(intent.getStringExtra(getString(R.string.id_value_key)));
        checkBoxAndroid.setChecked(intent.getBooleanExtra(getString(R.string.is_android_key), false));
    }
}
