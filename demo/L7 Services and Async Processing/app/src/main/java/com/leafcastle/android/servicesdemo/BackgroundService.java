package com.leafcastle.android.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BackgroundService extends Service {

    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT = "com.leafcastle.android.servicesdemo.BROADCAST_BACKGROUND_SERVICE_RESULT";
    public static final String EXTRA_TASK_RESULT = "task_result";
    public static final String EXTRA_TASK_TIME_MS = "task_time";
    private static final String LOG = "BG_SERVICE";

    private boolean started = false;
    private long wait;

    private static final long DEFAULT_WAIT = 30*1000; //default = 30s

    public BackgroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "Background service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //in this case we only start the background running loop once
        if(!started && intent!=null) {
            wait = intent.getLongExtra(EXTRA_TASK_TIME_MS, DEFAULT_WAIT);
            Log.d(LOG, "Background service onStartCommand with wait: " + wait + "ms");
            started = true;

            //doBackgroundThing(wait);
        } else {
            Log.d(LOG, "Background service onStartCommand - already started!");
        }
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //this service is not for binding: return null.
        return null;
    }

    private void doBackgroundThing(final long waitTimeInMilis){


        AsyncTask<Object, Object, String> task = new AsyncTask<Object, Object, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Object[] params) {
                String s = "Background job";
                try {
                    Log.d(LOG, "Task started");
                    Thread.sleep(waitTimeInMilis);
                    Log.d(LOG, "Task completed");
                } catch (Exception e) {
                    s+= " did not finish due to error";
                    //e.printStackTrace();
                    return s;
                }

                s += " completed after " + waitTimeInMilis + "ms";
                return s;
            }


            @Override
            protected void onPostExecute(String stringResult) {
                super.onPostExecute(stringResult);
                broadcastTaskResult(stringResult);

                //if Service is still running, keep doing this recursively
                if(started){
                    doBackgroundThing(waitTimeInMilis);
                }
            }
        };

        task.execute();

    }

    private void broadcastTaskResult(String result){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_BACKGROUND_SERVICE_RESULT);
        broadcastIntent.putExtra(EXTRA_TASK_RESULT, result);
        Log.d(LOG, "Broadcasting:" + result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        started = false;
        Log.d(LOG,"Background service destroyed");
        super.onDestroy();
    }
}
