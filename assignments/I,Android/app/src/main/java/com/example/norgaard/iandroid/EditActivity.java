package com.example.norgaard.iandroid;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    public static final String SAVING_ANDROID_KEY = "saving android key";
    public static final String SAVING_NAME_KEY = "saving name key";
    public static final String SAVING_ID_KEY = "saving id key";

    Context context = this;

    RadioGroup androidGroup;
    EditText name;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        androidGroup = (RadioGroup) findViewById(R.id.radioGroupAndroid);
        name = (EditText) findViewById(R.id.fieldName);
        id = (EditText) findViewById(R.id.fieldId);

        if (savedInstanceState != null) {
            String n = savedInstanceState.getString(SAVING_NAME_KEY);
            String i = savedInstanceState.getString(SAVING_ID_KEY);
            int a = savedInstanceState.getInt(SAVING_ANDROID_KEY);

            androidGroup.check(a);
            name.setText(n);
            id.setText(i);
        }
        else {
            Intent intent = getIntent();

            Boolean a = intent.getBooleanExtra(getString(R.string.extra_put_android_key), false);
            String n = intent.getStringExtra(getString(R.string.extra_put_name_key));
            String i = intent.getStringExtra(getString(R.string.extra_put_id_key));

            name.setText(n);
            id.setText(i);
            if (a) {
                androidGroup.check(R.id.radioButtonYes);
            }
            else {
                androidGroup.check(R.id.radioButtonNo);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putInt(SAVING_ANDROID_KEY, androidGroup.getCheckedRadioButtonId());
        outState.putString(SAVING_NAME_KEY, name.getText().toString());
        outState.putString(SAVING_ID_KEY, id.getText().toString());

        super.onSaveInstanceState(outState, outPersistentState);
    }
}
