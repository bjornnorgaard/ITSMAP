package com.example.norgaard.iandroid;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    public static final String SAVING_STATE_OF_NAME_FIELD = "saving state of name field";
    public static final String SAVING_STATE_OF_ID_FIELD = "saving state of id field";
    public static final String SAVING_IS_ANDRODI_STATE = "saving is androdi state";
    EditText editTextName;
    EditText editTextId;
    RadioGroup radioGroupIsAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //<editor-fold desc="Find View By Id">
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        radioGroupIsAndroid = (RadioGroup) findViewById(R.id.radioGroupIsAndroid);
        //</editor-fold>

        if (savedInstanceState != null) {
            radioGroupIsAndroid.check(Integer.parseInt(savedInstanceState.getString(SAVING_IS_ANDRODI_STATE)));
            editTextName.setText(savedInstanceState.getString(SAVING_STATE_OF_NAME_FIELD));
            editTextId.setText(savedInstanceState.getString(SAVING_STATE_OF_ID_FIELD));
        }
        else {
            Intent intent = getIntent();

            String name = intent.getStringExtra(getString(R.string.name_field_key));
            if (name != null) {
                editTextName.setText(name);
            }

            String id = intent.getStringExtra(getString(R.string.id_value_key));
            if (id != null) {
                editTextId.setText(id);
            }

            int android = intent.getIntExtra(getString(R.string.is_android_key), R.id.radioButtonNo);
            radioGroupIsAndroid.check(android);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(SAVING_IS_ANDRODI_STATE, radioGroupIsAndroid.getCheckedRadioButtonId());
        outState.putString(SAVING_STATE_OF_NAME_FIELD, editTextName.getText().toString());
        outState.putString(SAVING_STATE_OF_ID_FIELD, editTextId.getText().toString());
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
