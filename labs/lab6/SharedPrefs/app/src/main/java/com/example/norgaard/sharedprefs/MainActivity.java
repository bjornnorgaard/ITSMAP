package com.example.norgaard.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    SharedPreferences sharedPreferences;

    private EditText phonenumberEditText;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumberEditText = (EditText) findViewById(R.id.phonenumberEditText);
        firstnameEditText = (EditText) findViewById(R.id.firstnameEditText);
        lastnameEditText = (EditText) findViewById(R.id.lastnameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);

        sharedPreferences = context.getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
            restoreShit(); // Should be restored from bundle for performance reasons
        }
        else {
            restoreShit();
        }
    }

    private void restoreShit() {
        firstnameEditText.setText(sharedPreferences.getString(getString(R.string.saved_firstname), "No firstname"));
        phonenumberEditText.setText(sharedPreferences.getString(getString(R.string.saved_phonenumber), "No phonenumber"));
        lastnameEditText.setText(sharedPreferences.getString(getString(R.string.saved_lastname), "No lastname"));
        ageEditText.setText(sharedPreferences.getString(getString(R.string.saved_age), "No age"));
    }

    @Override
    protected void onPause() {
        saveShit();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveShit(); // Should be saved to bundle for performance reasons
        super.onSaveInstanceState(outState);
    }

    private void saveShit() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.saved_phonenumber), phonenumberEditText.getText().toString());
        editor.putString(getString(R.string.saved_firstname), firstnameEditText.getText().toString());
        editor.putString(getString(R.string.saved_lastname), lastnameEditText.getText().toString());
        editor.putString(getString(R.string.saved_age), ageEditText.getText().toString());
        editor.commit();
    }
}
