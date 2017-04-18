package itsmap.test.android.leafcastlelabs.com.imagebuttontest;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SAVED_STATE_SWAPPED = "saved_state_swapped";

    private Button btnSwap;
    private ImageView imgLeft;
    private ImageView imgRight;
    private boolean swapped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //note that this app uses RelativeLayout and not ConstraintLayout

        Log.d("Arnie", "onCreate called");

        if(savedInstanceState!=null){
            swapped = savedInstanceState.getBoolean(SAVED_STATE_SWAPPED, false);
        }




       btnSwap = (Button)findViewById(R.id.btnSwap);

       btnSwap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //swap images
               //Toast.makeText(getApplicationContext(), "Swapping Arnie...", Toast.LENGTH_SHORT).show();
               swap();
               updateImages(swapped);

           }
       });




       imgLeft = (ImageView)findViewById(R.id.imgLeft);
       imgLeft.setOnClickListener(this.imageClickListener);

       imgRight = (ImageView)findViewById(R.id.imgRight);
       imgRight.setOnClickListener(this.imageClickListener);

       updateImages(swapped);

    }

    private void swap(){

        swapped = swapped ? false : true;
    }

    public void buttonClicked(View v){

        Toast.makeText(this, "Well done, you did not crash the system", Toast.LENGTH_SHORT).show();
    }

    private void updateImages(boolean isSwapped){

        if(isSwapped){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.arnie1, getTheme()));
                imgRight.setImageDrawable(getResources().getDrawable(R.drawable.arnie2, getTheme()));

            } else {
                imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.arnie1));
                imgRight.setImageDrawable(getResources().getDrawable(R.drawable.arnie2));
            }
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.arnie2, getTheme()));
                imgRight.setImageDrawable(getResources().getDrawable(R.drawable.arnie1, getTheme()));

            } else {
                imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.arnie2));
                imgRight.setImageDrawable(getResources().getDrawable(R.drawable.arnie1));

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(SAVED_STATE_SWAPPED, swapped);

        super.onSaveInstanceState(outState);
    }

    private View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            swap();
            updateImages(swapped);
        }
    };
}
