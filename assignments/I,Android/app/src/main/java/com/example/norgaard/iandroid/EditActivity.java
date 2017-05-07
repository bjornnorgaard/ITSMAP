package com.example.norgaard.iandroid;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    public static final String SAVING_ANDROID_KEY = "saving androidCheckBox key";
    public static final String SAVING_NAME_KEY = "saving nameTextView key";
    public static final String SAVING_ID_KEY = "saving idTextView key";

    Context context = this;

    RadioGroup androidGroup;
    EditText name;
    EditText id;

    Button cancel;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        androidGroup = (RadioGroup) findViewById(R.id.radioGroupAndroid);
        name = (EditText) findViewById(R.id.fieldName);
        id = (EditText) findViewById(R.id.fieldId);

        cancel = (Button) findViewById(R.id.buttonCancel);
        ok = (Button) findViewById(R.id.buttonOk);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String i = id.getText().toString();
                Boolean android;

                int a = androidGroup.getCheckedRadioButtonId();
                if (a == R.id.radioButtonYes) {
                    android = true;
                }
                else {
                    android = false;
                }

                Intent intent = new Intent();
                intent.putExtra(getString(R.string.extra_put_android_key), android);
                intent.putExtra(getString(R.string.extra_put_name_key), n);
                intent.putExtra(getString(R.string.extra_put_id_key), i);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putInt(SAVING_ANDROID_KEY, androidGroup.getCheckedRadioButtonId());
        outState.putString(SAVING_NAME_KEY, name.getText().toString());
        outState.putString(SAVING_ID_KEY, id.getText().toString());

        super.onSaveInstanceState(outState, outPersistentState);
    }
}
