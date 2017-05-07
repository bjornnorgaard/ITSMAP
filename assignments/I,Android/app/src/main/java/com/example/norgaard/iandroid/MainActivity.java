package com.example.norgaard.iandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SAVING_ANDROID_KEY = "saving androidCheckBox key";
    public static final String SAVING_BITMAP_KEY = "saving bitmap key";
    public static final String SAVING_NAME_KEY = "saving nameTextView key";
    public static final String SAVING_ID_KEY = "saving idTextView key";

    public static final int REQUEST_CODE_TAKE_PICTURE = 100;
    public static final int REQUEST_CODE_EDIT_PROFILE = 101;

    Context context = this;

    ImageView pictureImageView;
    CheckBox androidCheckBox;
    TextView nameTextView;
    TextView idTextView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pictureImageView = (ImageView) findViewById(R.id.imageViewProfilePicture);
        androidCheckBox = (CheckBox) findViewById(R.id.checkBoxIdAndroid);
        nameTextView = (TextView) findViewById(R.id.valueName);
        idTextView = (TextView) findViewById(R.id.valueId);
        androidCheckBox.setClickable(false);

        if (savedInstanceState != null) {
            bitmap = (Bitmap) savedInstanceState.getParcelable(SAVING_BITMAP_KEY);
            if (bitmap != null) {
                pictureImageView.setImageBitmap(bitmap);
            }
            androidCheckBox.setChecked(savedInstanceState.getBoolean(SAVING_ANDROID_KEY, false));
            nameTextView.setText(savedInstanceState.getString(SAVING_NAME_KEY));
            idTextView.setText(savedInstanceState.getString(SAVING_ID_KEY));
        }

        pictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    private void takePicture() {
        if (getCameraPermission() == false) {
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
        else {
            Toast.makeText(this, R.string.sorry_no_camera_found, Toast.LENGTH_SHORT).show();
        }
    }

    // The following method is taken from:
    // https://developer.android.com/training/permissions/requesting.html
    // used with a few changes.
    private boolean getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
        else {
            return true;
        }

        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void editProfile() {
        Intent intent = new Intent(context, EditActivity.class);

        intent.putExtra(getString(R.string.extra_put_id_key), idTextView.getText().toString());
        intent.putExtra(getString(R.string.extra_put_name_key), nameTextView.getText().toString());
        intent.putExtra(getString(R.string.extra_put_android_key), androidCheckBox.isChecked());

        startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    pictureImageView.setImageBitmap(bitmap);
                }
            }
            else {
                Toast.makeText(this, R.string.camera_failed, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_CODE_EDIT_PROFILE) {
            if (resultCode == RESULT_OK) {
                String id = data.getStringExtra(getString(R.string.extra_put_id_key));
                Boolean idAndroid = data.getBooleanExtra(getString(R.string.extra_put_android_key), false);
                String name = data.getStringExtra(getString(R.string.extra_put_name_key));

                androidCheckBox.setChecked(idAndroid);
                nameTextView.setText(name);
                idTextView.setText(id);
            }
            else {
                Toast.makeText(this, R.string.edit_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(SAVING_NAME_KEY, nameTextView.getText().toString());
        outState.putBoolean(SAVING_ANDROID_KEY, androidCheckBox.isChecked());
        outState.putString(SAVING_ID_KEY, idTextView.getText().toString());
        outState.putParcelable(SAVING_BITMAP_KEY, bitmap);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
