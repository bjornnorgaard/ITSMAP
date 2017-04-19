package com.example.norgaard.iandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextId;
    RadioGroup radioGroupIsAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // FindById and stuff
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        radioGroupIsAndroid = (RadioGroup) findViewById(R.id.radioGroupIsAndroid);

        Intent intent = getIntent();

        String name = intent.getStringExtra(getString(R.string.name_field_key));
        if (name != null) {
            editTextName.setText(name);
        }

        String id = intent.getStringExtra(getString(R.string.id_value_key));
        if (id != null) {
            editTextId.setText(id);
        }

        boolean android = intent.getBooleanExtra(getString(R.string.is_android_key), false);
        if (android) {
            radioGroupIsAndroid.check(R.id.radioYes);
        }
        else {
            radioGroupIsAndroid.check(R.id.radioNo);
        }
    }
}
