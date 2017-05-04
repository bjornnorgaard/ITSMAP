package com.leafcastlelabs.demoui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class ImageViewActivity extends Activity {


    private ImageSwitcher switcher;
    private Button btnNext;
    private Button btnPrev;
    private TextView txtCaption;

    private int currentIndex = 0;
    private int numPics = 3;

    private int[] drawablesList = {R.drawable.arnie_conan, R.drawable.arnie_predator, R.drawable.arnie_not_android};
    private int[] stringsList = {R.string.arnie_quote_1, R.string.arnie_quote_2, R.string.arnie_quote_3};

    private Animation in;
    private Animation out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        txtCaption = (TextView)findViewById(R.id.txtCaption);

        switcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView createView = new ImageView(getApplicationContext());
                createView.setScaleType(ImageView.ScaleType.FIT_XY);
                createView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return createView;
            }
        });

        in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);


        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView(goRight());
            }
        });
        btnPrev = (Button)findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView(goLeft());
            }
        });

        switchView(currentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_view, menu);
        return true;
    }

    private int goLeft(){
        currentIndex--;
        if(currentIndex<0){
            currentIndex = 0;
        }
        return currentIndex;
    }

    private int goRight(){
        currentIndex++;
        if(currentIndex>=drawablesList.length){
            currentIndex = drawablesList.length-1;
        }
        return currentIndex;
    }

    private void switchView(int index){

        if(index>-1 && index<drawablesList.length){
            switcher.setImageResource(drawablesList[index]);
            if(index<stringsList.length) {
                txtCaption.setText(index + " " + getString(stringsList[index]));
            }
        }
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
