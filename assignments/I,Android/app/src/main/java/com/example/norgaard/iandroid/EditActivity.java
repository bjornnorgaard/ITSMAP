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

    RadioGroup isAndroidCheckBoxRadioGroup;
    EditText nameEditText;
    EditText idEditText;
    Button cancelButton;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        isAndroidCheckBoxRadioGroup = (RadioGroup) findViewById(R.id.radioGroupAndroid);
        nameEditText = (EditText) findViewById(R.id.fieldName);
        idEditText = (EditText) findViewById(R.id.fieldId);

        cancelButton = (Button) findViewById(R.id.buttonCancel);
        okButton = (Button) findViewById(R.id.buttonOk);

        if (savedInstanceState != null) {
            int isAndroid = savedInstanceState.getInt(SAVING_ANDROID_KEY);
            String name = savedInstanceState.getString(SAVING_NAME_KEY);
            String id = savedInstanceState.getString(SAVING_ID_KEY);

            isAndroidCheckBoxRadioGroup.check(isAndroid);
            nameEditText.setText(name);
            idEditText.setText(id);
        }
        else {
            Intent intent = getIntent();

            Boolean isAndroid = intent.getBooleanExtra(getString(R.string.extra_put_android_key), false);
            String name = intent.getStringExtra(getString(R.string.extra_put_name_key));
            String id = intent.getStringExtra(getString(R.string.extra_put_id_key));

            nameEditText.setText(name);
            idEditText.setText(id);
            if (isAndroid) {
                isAndroidCheckBoxRadioGroup.check(R.id.radioButtonYes);
            }
            else {
                isAndroidCheckBoxRadioGroup.check(R.id.radioButtonNo);
            }
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String id = idEditText.getText().toString();
                Boolean isAndroid;

                int selctedIdAndroidCheckBoxId = isAndroidCheckBoxRadioGroup.getCheckedRadioButtonId();
                if (selctedIdAndroidCheckBoxId == R.id.radioButtonYes) {
                    isAndroid = true;
                }
                else {
                    isAndroid = false;
                }

                Intent intent = new Intent();

                intent.putExtra(getString(R.string.extra_put_android_key), isAndroid);
                intent.putExtra(getString(R.string.extra_put_name_key), name);
                intent.putExtra(getString(R.string.extra_put_id_key), id);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putInt(SAVING_ANDROID_KEY, isAndroidCheckBoxRadioGroup.getCheckedRadioButtonId());
        outState.putString(SAVING_NAME_KEY, nameEditText.getText().toString());
        outState.putString(SAVING_ID_KEY, idEditText.getText().toString());

        super.onSaveInstanceState(outState, outPersistentState);
    }
}
