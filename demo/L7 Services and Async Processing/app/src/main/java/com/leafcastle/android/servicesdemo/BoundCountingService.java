package com.leafcastle.android.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BoundCountingService extends Service {

    private int count;
    private boolean running = false;

    public class CountingServiceBinder extends Binder {

        BoundCountingService getService() {
            return BoundCountingService.this;
        }
    }

    private final IBinder binder = new CountingServiceBinder();

    public BoundCountingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        count = 0;
        running = true;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(running == true) {
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    //note we do not override onStartCommand for this bound service;

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    public int getCount(){
        return count;
    }


}
