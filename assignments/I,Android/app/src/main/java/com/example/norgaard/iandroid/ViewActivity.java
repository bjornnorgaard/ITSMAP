package com.example.norgaard.iandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_TAKE_PICTURE = 100;
    private static final int REQUEST_CODE_EDIT_PROFILE_KEY = 101;

    public static final String SAVING_NAME_FIELD_KEY = "saving name field key";
    public static final String SAVING_ID_FIELD_KEY = "saving id field key";
    public static final String SAVING_IS_ANDROID_KEY = "saving is android key";

    ImageView imageViewProfilePicture;
    private Bitmap imageThumbnail;
    Context context = this;

    CheckBox isAndroid;
    TextView nameField;
    TextView idField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameField = (TextView) findViewById(R.id.textViewLabelName);
        isAndroid = (CheckBox) findViewById(R.id.checkBoxIsAndroid);
        idField = (TextView) findViewById(R.id.textViewLabelId);

        if (savedInstanceState != null) {
            isAndroid.setChecked(savedInstanceState.getBoolean(SAVING_IS_ANDROID_KEY));
            nameField.setText(savedInstanceState.getString(SAVING_NAME_FIELD_KEY));
            idField.setText(savedInstanceState.getString(SAVING_ID_FIELD_KEY));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        imageViewProfilePicture = (ImageView) findViewById(R.id.imageViewProfile);
        imageViewProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePicture();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(SAVING_ID_FIELD_KEY, Integer.parseInt(idField.getText().toString()));
        outState.putString(SAVING_NAME_FIELD_KEY, nameField.getText().toString());
        outState.putBoolean(SAVING_IS_ANDROID_KEY, isAndroid.isChecked());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    imageThumbnail = (Bitmap) bundle.get("data");
                    imageViewProfilePicture.setImageBitmap(imageThumbnail);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void EditProfile() {
        Intent intent = new Intent(context, EditActivity.class);

        intent.putExtra(getString(R.string.name_field_key), nameField.getText());
        intent.putExtra(getString(R.string.id_value_key), idField.getText());

        if (isAndroid.isChecked()) {
            intent.putExtra(getString(R.string.is_android_key), R.id.radioButtonYes);
        }
        else {
            intent.putExtra(getString(R.string.is_android_key), R.id.radioButtonNo);
        }

        startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE_KEY);
    }

    private void TakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
        else {
            Toast.makeText(this, "Sorry no camera", Toast.LENGTH_SHORT).show();
        }
    }
}

