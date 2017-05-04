package com.leafcastle.android.datastoragedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.leafcastle.android.datastoragedemo.adaptors.TaskAdaptor;
import com.leafcastle.android.datastoragedemo.model.Place;
import com.leafcastle.android.datastoragedemo.model.Task;
import com.leafcastle.android.datastoragedemo.utils.DatabaseHelper;
import com.leafcastle.android.datastoragedemo.utils.Globals;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper; //our database helper = database abstraction
    private ListView lstTasks;
    private List<Task> tasksFromDb;
    private TaskAdaptor adaptor;
    private Button btnAddTask;
    private EditText edtTaskName, edtTaskPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Stetho initialization - allows for debugging features in Chrome browser
           See http://facebook.github.io/stetho/ for details
           1) Open chrome://inspect/ in a Chrome browse
           2) select 'inspect' on your app under the specific device/emulator
           3) select resources tab
           4) browse database tables under Web SQL
         */
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build());
        /* end Stethos */

        lstTasks = (ListView) findViewById(R.id.listView);
        edtTaskName = (EditText) findViewById(R.id.edtTaskName);
        edtTaskPlace = (EditText) findViewById(R.id.edtTaskPlace);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
            btnAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTask();
                }
            });

        initDatabase();

        updateTaskList();

        adaptor = new TaskAdaptor(this, tasksFromDb);
        lstTasks.setAdapter(adaptor);
        lstTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task t = tasksFromDb.get(position);
                if(t!=null){
                    //not the most efficient way, but we need to delete item in DB and update our listview
                    getDatabase().deleteTask(t.getId());
                    updateTaskList();
                    adaptor.setTasks(tasksFromDb);
                    adaptor.notifyDataSetChanged();
                }
            }
        });
    }

    private boolean initDatabase(){

        //just for giggles and the exercise we use SharedPreferences to check if database already initialized
        SharedPreferences prefs = getSharedPreferences(Globals.SHARED_PREFS_NAME, MODE_PRIVATE);
        if(prefs.getBoolean(Globals.PREFS_KEY_BOOLEAN_DB_INITIALIZED, false)){
            //database already initialized
            return true;
        }

        dbHelper = getDatabase();

        Place home = new Place("Home");
        Place work = new Place("Work");

        home.setId(dbHelper.createPlace(home));
        work.setId(dbHelper.createPlace(work));

        Task t1 = new Task("Prepare lecture",work, 0, dbHelper.createDateTimeString(new Date(System.currentTimeMillis() + 24*60*60*1000)));
        Task t2 = new Task("Prepare database demo",home, 0, dbHelper.createDateTimeString(new Date(System.currentTimeMillis() + 36*60*60*1000)));
        Task t3 = new Task("Watch Arnie movies",home, 0, dbHelper.createDateTimeString(new Date(System.currentTimeMillis() + 48*60*60*1000)));

        t1.setId(dbHelper.createTask(t1));
        t2.setId(dbHelper.createTask(t2));
        t3.setId(dbHelper.createTask(t3));

        //update sharedPreferences to reflect that we initialized the database
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Globals.PREFS_KEY_BOOLEAN_DB_INITIALIZED, true);
        editor.commit();

        return true;
    }

    private void addTask(){

        String task = edtTaskName.getText().toString();
        String place = edtTaskPlace.getText().toString();
        if(task==null || task.equals("")){
            Toast.makeText(MainActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
        } else if(place==null || place.equals("")){
            Toast.makeText(MainActivity.this, "Please enter a task place name", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper = getDatabase();
            Place p = new Place(place);
            p.setId(dbHelper.createPlace(p));

            Task t = new Task(task, p, 0, dbHelper.createDateTimeString(new Date(System.currentTimeMillis() + 24*60*60*1000)));
            dbHelper.createTask(t);
            updateTaskList();
            dbHelper.closeDB();

            //update UI
            edtTaskName.setText("");
            edtTaskPlace.setText("");
            adaptor.setTasks(tasksFromDb);
            ((BaseAdapter)lstTasks.getAdapter()).notifyDataSetChanged();

        }

    }

    private void updateTaskList(){

        dbHelper = getDatabase();
        tasksFromDb = dbHelper.getAllTasks();
        for(Task t : tasksFromDb){
            t.setPlace(dbHelper.getPlaceByTask(t));   //get places for tasks //TODO: do this in DB Helper
        }
        dbHelper.closeDB();


    }

    private DatabaseHelper getDatabase(){
        if(dbHelper ==null){
            dbHelper = new DatabaseHelper(getApplicationContext());
        }
        return dbHelper;
    }
}
