package com.leafcastle.android.clickcounter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String CLICK_COUNT = "click_count";

    TextView txtCount;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check if there was any state passed to us from Android from a previous instance
        if(savedInstanceState != null){
            count = savedInstanceState.getInt(CLICK_COUNT, 0); //get our count from previous state. Default to zero if none found
        }

        txtCount = (TextView)findViewById(R.id.txtCount);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCount);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                updateUI();
            }
        });

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Android calls here when it is time to save our instance state
        //Add the your data to the bundle
        outState.putInt(CLICK_COUNT, count);
        Log.d("Main", "Click count saved to instance state");
        super.onSaveInstanceState(outState);
    }

    private void updateUI(){
        txtCount.setText("" + count);
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

        //defined a few menu items in res/menu/menu_main.xml
        if (id == R.id.action_settings) {
            Toast.makeText(this, R.string.text_settings_not_implemented, Toast.LENGTH_SHORT).show();
            return true;
        }

        //reset the count and update iu if reset action is clicked in menu
        if (id == R.id.menuReset) {
            count = 0;
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}