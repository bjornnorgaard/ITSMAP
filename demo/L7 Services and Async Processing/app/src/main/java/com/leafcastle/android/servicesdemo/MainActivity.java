package com.leafcastle.android.servicesdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MAIN";
    //buttons
    private Button btnStartBgService, btnStopBgService, btnBindCountingService, btnUnbindCountingService, btnGetCount, btnFoo, btnBaz, btnExit;
    private TextView txtToUpdate;

    //for background service
    private long task_time = 4*1000; //4 ms

    //for bound counting service
    private BoundCountingService countingService;
    private ServiceConnection countingServiceConnection;
    private boolean bound = false;
    private int count;

    private int fooCount, bazCount, fooBazCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnStartBgService = (Button) findViewById(R.id.btnStartBgService);
        btnStartBgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundService(task_time);
            }
        });

        btnStopBgService = (Button) findViewById(R.id.btnStopBgService);
        btnStopBgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBackgroundService();
            }
        });

        btnBindCountingService = (Button) findViewById(R.id.btnBindCoutningService);
        btnBindCountingService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bindToCountingService();
            }
        });

        btnUnbindCountingService = (Button) findViewById(R.id.btnUnbindCountingService);
        btnUnbindCountingService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindFromCountingService();
            }
        });

        btnGetCount = (Button) findViewById(R.id.btnGetCount);
        btnGetCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(bound && countingService!=null){
                   count = countingService.getCount();
                   //update textView
                   Toast.makeText(MainActivity.this, "Count is " + count, Toast.LENGTH_SHORT).show();
               }
            }
        });

        btnFoo = (Button) findViewById(R.id.btnFoo);
        btnFoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fooService();
            }
        });

        btnExit = (Button) findViewById(R.id.btExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBaz = (Button) findViewById(R.id.btnBaz);
        btnBaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bazService();
            }
        });

        txtToUpdate = (TextView) findViewById(R.id.txtToUpdate);

        setupConnectionToCountingService();

        //example of asynch task from UI thread, runs every time onCreate is called
        AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String[] strings) {
                //everything in this method is asynch and will not block UI thread
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //txtToUpdate.setText(strings[0]); //this creates a compile time error
                return strings[0];

            }

            @Override
            protected void onPostExecute(String s) {
                //everything in this method is synched with main thread
                //you can update UI widgets from there.
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                txtToUpdate.setText(s); //this is fine
            }
        };
        asyncTask.execute("Yo! from the backgrund!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "registering receivers");

        IntentFilter filter = new IntentFilter();
        filter.addAction(BackgroundService.BROADCAST_BACKGROUND_SERVICE_RESULT);

        //can use registerReceiver(...)
        //but using local broadcasts for this service:
        LocalBroadcastManager.getInstance(this).registerReceiver(onBackgroundServiceResult, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "unregistering receivers");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onBackgroundServiceResult);

    }

    private void setupConnectionToCountingService(){
        countingServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  Because we have bound to a explicit
                // service that we know is running in our own process, we can
                // cast its IBinder to a concrete class and directly access it.
                //ref: http://developer.android.com/reference/android/app/Service.html
                countingService = ((BoundCountingService.CountingServiceBinder)service).getService();
                Log.d(LOG, "Counting service connected");

            }

            public void onServiceDisconnected(ComponentName className) {
                // This is called when the connection with the service has been
                // unexpectedly disconnected -- that is, its process crashed.
                // Because it is running in our same process, we should never
                // see this happen.
                //ref: http://developer.android.com/reference/android/app/Service.html
                countingService = null;
                Log.d(LOG, "Counting service disconnected");
            }
        };
    }

    void bindToCountingService() {
        bindService(new Intent(MainActivity.this,
                BoundCountingService.class), countingServiceConnection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    void unBindFromCountingService() {
        if (bound) {
            // Detach our existing connection.
            unbindService(countingServiceConnection);
            bound = false;
        }
    }

    private void startBackgroundService(long taskTime){
        Intent backgroundServiceIntent = new Intent(MainActivity.this, BackgroundService.class);
        backgroundServiceIntent.putExtra(BackgroundService.EXTRA_TASK_TIME_MS, taskTime);
        startService(backgroundServiceIntent);
    }

    private void stopBackgroundService(){
        Intent backgroundServiceIntent = new Intent(MainActivity.this, BackgroundService.class);
        stopService(backgroundServiceIntent);
    }

    private void handleBackgroundResult(String result){
        Toast.makeText(MainActivity.this, "Got result from background service:\n" + result, Toast.LENGTH_SHORT).show();
    }

    private void fooService(){
        fooCount++;
        fooBazCount++;
        IntentServiceForOffloadingTasks.startActionFoo(this, "FOO" + fooCount, "" + fooBazCount);
    }

    private void bazService(){
        bazCount++;
        fooBazCount++;
        IntentServiceForOffloadingTasks.startActionBaz(this, "BAZ"+bazCount, "" + fooBazCount);
    }

    private BroadcastReceiver onBackgroundServiceResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG, "Broadcast reveiced from bg service");
            String result = intent.getStringExtra(BackgroundService.EXTRA_TASK_RESULT);
            if(result==null){
                result = getString(R.string.err_bg_service_result);
            }
            handleBackgroundResult(result);
        }
    };

}
