package com.leafcastle.android.servicesdemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentServiceForOffloadingTasks} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServiceForOffloadingTasks extends IntentService {

    private static final String LOG = "INTENT_SERVICE";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.leafcastle.android.servicesdemo.action.FOO";
    private static final String ACTION_BAZ = "com.leafcastle.android.servicesdemo.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.leafcastle.android.servicesdemo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.leafcastle.android.servicesdemo.extra.PARAM2";

    public IntentServiceForOffloadingTasks() {
        super("IntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentServiceForOffloadingTasks
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, IntentServiceForOffloadingTasks.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentServiceForOffloadingTasks
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, IntentServiceForOffloadingTasks.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        if(param1==null){
            param1 = "undefined";
        }
        if(param2==null){
            param2 = "undefined";
        }
        try {
            Log.d(LOG, "Foo started: " + param1 + " : " + param2);
            Thread.sleep(500); //we can do asynch stuff here, because: intent service
            Log.d(LOG, "Foo completed");
        } catch (Exception e) {
            Log.d(LOG, "Foo exception");
            //e.printStackTrace();
        }

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        if(param1==null){
            param1 = "undefined";
        }
        if(param2==null){
            param2 = "undefined";
        }
        try {
            Log.d(LOG, "Baz started: " + param1 + " : " + param2);
            Thread.sleep(1500); //we can do asynch stuff here, because: intent service
            Log.d(LOG, "Baz completed");
        } catch (Exception e) {
            Log.d(LOG, "Baz exception");
            //e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }
}
