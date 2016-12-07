package com.android.calorieCalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter1
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_FULL_NAME = "Full_Name";
    public static final String KEY_HEIGHT = "Height";
    public static final String KEY_WEIGHT = "Weight";   
    public static final String KEY_SEX = "Sex";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_PASSWORD = "Password";
    private static final String TAG = "DBAdapter1";
    private static final String DATABASE_NAME = "profiles";
    private static final String DATABASE_TABLE = "users";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table users (_id integer primary key autoincrement, "
        + "Full_Name text not null," + "Height integer not null," +  "Weight integer not null,"
        + "Sex text not null," + "Username text not null,"
        + "Password not null);";
        
    private final Context context; 
     
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private boolean opened = false;
    
    public DBAdapter1(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	
        	db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    private SQLiteDatabase open()
    {
		if(!opened)
			db = DBHelper.getWritableDatabase();
		opened = true;
        return db;
    }

    //---closes the database---    
    private void close() 
    {
		if(opened)
			DBHelper.close();
		opened = false;
    }
    
    //---insert a title into the database---
    public long insertTitle(String name, int height, int weight, String sex, String username, String password) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FULL_NAME , name);
        initialValues.put(KEY_HEIGHT, height);
        initialValues.put(KEY_WEIGHT, weight);
        initialValues.put(KEY_SEX , sex);
        initialValues.put(KEY_USERNAME , username);
        initialValues.put(KEY_PASSWORD , password);
        open();
        long erg = db.insert(DATABASE_TABLE, null, initialValues);
        close();
        return erg;
    }

    //---deletes a particular title---
    public boolean deleteTitle(long rowId) 
    {
		open();
		int erg = db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null);
		close();
	    return erg > 0;        		
    }

    //---retrieves all the titles---
    public Cursor getAllTitles() 
    {
    	open();
        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_FULL_NAME,
        		KEY_HEIGHT,
        		KEY_WEIGHT,
        		KEY_SEX,
        		KEY_USERNAME,
        		KEY_PASSWORD}, 
                null, 
                null, 
                null, 
                null, 
                null);
        close();
        return (mCursor.moveToFirst()) ? mCursor : null;
    }

    //---retrieves a particular title---
    public Cursor getTitle(long rowId) throws SQLException 
    {
    	open();
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID, 
                		KEY_FULL_NAME,
                		KEY_HEIGHT,
                		KEY_WEIGHT,
                		KEY_SEX,
                		KEY_USERNAME,
                		KEY_PASSWORD
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        close();
        return (mCursor.moveToFirst()) ? mCursor : null;
    }

    //---updates a title---
    public boolean updateTitle(long rowId, String name, int height, int weight, String sex, String username, String password)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_FULL_NAME , name);
        args.put(KEY_HEIGHT, height);
        args.put(KEY_WEIGHT, weight);
        args.put(KEY_SEX , sex);
        args.put(KEY_USERNAME , username);
        args.put(KEY_PASSWORD , password);
        open();
        int erg = db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null);
        close();
        return erg > 0;
    }
}
 
