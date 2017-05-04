package com.leafcastle.android.datastoragedemo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.leafcastle.android.datastoragedemo.model.Place;
import com.leafcastle.android.datastoragedemo.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by kasper on 24/04/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

        /*
         * This whole class was inspired by the example at:
         * http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
         * Code have been modifed from original example
         *
         * NB: This code example is incomplete and to partially untested - use for code demo/hack only :)
         */


    private static final String LOG = "DB_HELPER";

    // Database Version
    private static final int DATABASE_VERSION = 3;

    //TODO: Extract all constants for database schema in a contract class
    // Database Name
    private static final String DATABASE_NAME = "task_manager";

    // Table Names
    private static final String TABLE_TASK = "todos";
    private static final String TABLE_PLACE = "tasks";
    private static final String TABLE_TASK_PLACE = "task_places";

    // Common column names
    // All columns in all tables will have these vaules
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED = "created";

    // TASK Table - column names
    private static final String KEY_NAME = "task_name";
    private static final String KEY_STATUS = "task_status";

    // PLACE Table - column names
    private static final String KEY_PLACE_NAME = "place_name";

    // TASK_PLACES joint table - column names
    private static final String KEY_TASK_ID = "task_id";
    private static final String KEY_PLACE_ID = "place_id";

    // Table Create Statements
    private static final String CREATE_TABLE_TASK = "CREATE TABLE "
            + TABLE_TASK + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_PLACE = "CREATE TABLE " + TABLE_PLACE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME + " TEXT,"
            + KEY_CREATED + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_TASK_PLACE = "CREATE TABLE "
            + TABLE_TASK_PLACE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TASK_ID + " INTEGER," + KEY_PLACE_ID + " INTEGER,"
            + KEY_CREATED + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_TASK_PLACE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_PLACE);

        // create new tables
        onCreate(db);
    }

    //NOTE: task should have a place associated with it
    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_STATUS, task.getStatus());
        values.put(KEY_CREATED, getDateTime());

        // insert row
        long task_id = db.insert(TABLE_TASK, null, values);


        //insert in joint table if place associated with task
        Place p = task.getPlace();
        if(p !=null && p.getId()>=0) {
            createTaskPlace(task_id, p.getId());
        }

        return task_id;
    }

    public Task getTask(long task_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TASK + " WHERE "
                + KEY_ID + " = " + task_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Task task = new Task();
        task.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        task.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        task.setCreated(c.getString(c.getColumnIndex(KEY_CREATED)));

        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(c.getInt((c.getColumnIndexOrThrow(KEY_ID))));
                task.setName((c.getString(c.getColumnIndexOrThrow(KEY_NAME))));
                task.setCreated(c.getString(c.getColumnIndexOrThrow(KEY_CREATED)));

                // adding to todo list
                tasks.add(task);
            } while (c.moveToNext());
        }

        return tasks;
    }

    public List<Task> getAllTasksByPlace(String place_name) {
        List<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASK + " td, "
                + TABLE_PLACE + " tg, " + TABLE_TASK_PLACE + " tt WHERE tg."
                + KEY_PLACE_NAME + " = '" + place_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_PLACE_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_TASK_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                task.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                task.setCreated(c.getString(c.getColumnIndex(KEY_CREATED)));

                // adding to todo list
                tasks.add(task);
            } while (c.moveToNext());
        }

        return tasks;
    }


    public int getTaskCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_STATUS, task.getStatus());


        // updating row
        return db.update(TABLE_TASK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    public void deleteTask(long task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?",
                new String[] { String.valueOf(task_id) });
    }


    public long createPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getName());
        values.put(KEY_CREATED, getDateTime());

        // insert row
        long tag_id = db.insert(TABLE_PLACE, null, values);

        return tag_id;
    }

    public Place getPlace(long place_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACE + " WHERE "
                + KEY_ID + " = " + place_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Place place = new Place();
        place.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        place.setName((c.getString(c.getColumnIndex(KEY_PLACE_NAME))));

        return place;
    }

    public Place getPlaceByTask(Task task){
        long task_id = task.getId();

        String selectQuery = "SELECT  * FROM " + TABLE_TASK_PLACE + " WHERE "
                + KEY_TASK_ID + " = " + task_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Place p = null;
        if (c.moveToFirst()) {
                long place_id = c.getLong((c.getColumnIndex(KEY_PLACE_ID)));
                p = getPlace(place_id);
        }

        return p;
    }

    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<Place>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Place p = new Place();
                p.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                p.setName(c.getString(c.getColumnIndex(KEY_PLACE_NAME)));

                // adding to places list
                places.add(p);
            } while (c.moveToNext());
        }
        return places;
    }

    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getName());

        // updating row
        return db.update(TABLE_PLACE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(place.getId()) });
    }

    public void deletePlace(Place place, boolean alsoDeleteAssociatedTasks) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting place
        // check if task under this places should also be deleted
        if (alsoDeleteAssociatedTasks) {
            // get all tasks associated with this place
            List<Task> allTagToDos = getAllTasksByPlace(place.getName());

            // delete all tasks
            for (Task task : allTagToDos) {
                // delete task
                deleteTask(task.getId());
            }
        }

        // now delete the place
        db.delete(TABLE_PLACE, KEY_ID + " = ?",
                new String[] { String.valueOf(place.getId()) });
    }


    public long createTaskPlace(long task_id, long place_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_ID, task_id);
        values.put(KEY_PLACE_ID, place_id);
        values.put(KEY_CREATED, getDateTime());

        long id = db.insert(TABLE_TASK_PLACE, null, values);

        return id;
    }

    public int updateTaskPlace(long id, long place_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, place_id);

        // updating row
        return db.update(TABLE_TASK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteTaskPlace(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // closing databaseı
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public String getDateTime() {
        Date date = new Date();
        return createDateTimeString(date);
    }

    public String createDateTimeString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.format(date);
    }
}

