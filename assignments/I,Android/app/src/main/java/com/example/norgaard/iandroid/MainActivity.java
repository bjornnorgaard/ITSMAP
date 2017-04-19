package com.example.norgaard.iandroid;

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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SAVING_ANDROID_KEY = "saving android key";
    public static final String SAVING_BITMAP_KEY = "saving bitmap key";
    public static final String SAVING_NAME_KEY = "saving name key";
    public static final String SAVING_ID_KEY = "saving id key";

    public static final int REQUEST_CODE_TAKE_PICTURE = 100;

    Bitmap imageThumbnail;
    ImageView picture;
    CheckBox android;
    TextView name;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        picture = (ImageView) findViewById(R.id.imageViewProfilePicture);
        android = (CheckBox) findViewById(R.id.checkBoxIdAndroid);
        name = (TextView) findViewById(R.id.valueName);
        id = (TextView) findViewById(R.id.valueId);

        if (savedInstanceState != null) {
            picture.setImageBitmap((Bitmap) savedInstanceState.getParcelable(SAVING_BITMAP_KEY));
            android.setChecked(savedInstanceState.getBoolean(SAVING_ANDROID_KEY, false));
            name.setText(savedInstanceState.getString(SAVING_NAME_KEY));
            id.setText(savedInstanceState.getString(SAVING_ID_KEY));
        }

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePicture();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    imageThumbnail = (Bitmap) bundle.get("data");
                    picture.setImageBitmap(imageThumbnail);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(SAVING_NAME_KEY, name.getText().toString());
        outState.putBoolean(SAVING_ANDROID_KEY, android.isChecked());
        outState.putString(SAVING_ID_KEY, id.getText().toString());
        outState.putParcelable(SAVING_BITMAP_KEY, imageThumbnail);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void EditProfile() {

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
