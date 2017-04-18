package com.leafcastle.android.appflowtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ACTIVITY_2 = 100;
    public static final int REQUEST_CODE_TAKE_PICTURE = 101;

    public static final String PHOTO_THUMBNAIL = "photo_thumbnail";

    Button btnStart2;
    Button btnTakePicture;
    EditText edtUserinput;
    ImageView imgPhoto;

    Bitmap photoThumbnail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        edtUserinput = (EditText) findViewById(R.id.edtUserinput);
        btnStart2 = (Button) findViewById(R.id.btnStart2);
        btnStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start new activity
                startActivityTwo();
            }
        });

        btnTakePicture = (Button) findViewById(R.id.btnTakepicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take a picture
                takePicture();
            }
        });

        if (savedInstanceState != null) {
            photoThumbnail = savedInstanceState.getParcelable(PHOTO_THUMBNAIL);
            imgPhoto.setImageBitmap(photoThumbnail);
        }
    }

    private void takePicture() {
        //implicit Intent to take a picture using another application
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, REQUEST_CODE_TAKE_PICTURE);
        } else {
            Toast.makeText(this, "no camera, sorry", Toast.LENGTH_SHORT).show();
        }
    }

    public void startActivityTwo(){
        //explicit Intent to start the particular activity, pass some data as an Extra
        Intent intentStartActivity2 = new Intent(this, Activity2.class);
        intentStartActivity2.putExtra("user_input", edtUserinput.getText().toString());
        startActivityForResult(intentStartActivity2, REQUEST_CODE_ACTIVITY_2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        

        if(photoThumbnail!=null) {
            outState.putParcelable(PHOTO_THUMBNAIL, photoThumbnail);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_ACTIVITY_2){
            if(resultCode==RESULT_OK){

                if(data!=null){
                    String s = data.getStringExtra("user_input");
                    edtUserinput.setText(s);
                    Toast.makeText(this, "OK!" + s, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "OK!" + " but somehow got null data back", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "not OK", Toast.LENGTH_SHORT).show();
            }


        } else if(requestCode==REQUEST_CODE_TAKE_PICTURE){
            if(resultCode==RESULT_OK){
                //yay, we got a picture taken
                //check out: https://developer.android.com/training/camera/photobasics.html
                if(data!=null){
                    //lets use the thumbnail of the picture in the app
                    Bundle extras = data.getExtras();
                    photoThumbnail = (Bitmap) extras.get("data");
                    imgPhoto.setImageBitmap(photoThumbnail);
                }
            }

        }
    }
}
