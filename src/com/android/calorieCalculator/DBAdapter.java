package com.android.calorieCalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ITEM = "Item";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_UNIT = "Unit";   
    public static final String KEY_CALORIE_VALUE = "CalorieValue";    
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "nutrition";
    private static final String DATABASE_TABLE = "foods";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table foods (_id integer primary key autoincrement, "
        + "Item text not null, " + "Amount double not null, " +  "Unit text not null, " 
        + "CalorieValue integer not null);";
        
    private final Context context; 
	private boolean opened = false;
     
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    
    public DBAdapter(Context ctx) 
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
            db.execSQL("DROP TABLE IF EXISTS foods");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public void openDB()
    {
		if(!opened)
			db = DBHelper.getWritableDatabase();
		opened = true;
    }

    //---closes the database---    
    public void closeDB() 
    {
		if(opened) 
			DBHelper.close();			
		opened = false;
    }
    
    //---insert a title into the database---
    public long insertTitle(String item, double amount, String unit, int calorieValue) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put( KEY_ITEM , item);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_UNIT, unit);
        initialValues.put( KEY_CALORIE_VALUE , calorieValue);
        openDB();
        long erg = db.insert(DATABASE_TABLE, null, initialValues);
        closeDB();
		return erg;
    }
	
    //---deletes a particular title---
    public boolean deleteTitle(long rowId) 
    {
    	openDB();
		int erg = db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null);
		closeDB();
        return erg > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllTitles() 
    {
        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
					KEY_ROWID, 
					KEY_ITEM,
					KEY_AMOUNT,
					KEY_UNIT,
					KEY_CALORIE_VALUE}, 
					null, 
					null, 
					null, 
					null, 
					null);
        return (mCursor.moveToFirst()) ? mCursor : null;
    }

    //---retrieves a particular title---
    public Cursor getTitle(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_ITEM,
                		KEY_AMOUNT,
                		KEY_UNIT,
                		KEY_CALORIE_VALUE
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        return (mCursor.moveToFirst()) ? mCursor : null;
    }
	
	public Cursor getTitle(String item) throws SQLException 
	{
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_ITEM,
                		KEY_AMOUNT,
                		KEY_UNIT,
                		KEY_CALORIE_VALUE
                		}, 
                		KEY_ITEM + "=" + item, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);

        return (mCursor.moveToFirst()) ? mCursor : null;
	}
	
    //---updates a title---
    public boolean updateTitle(long rowId, String item, double amount, String unit, int calorieValue)
    {
        ContentValues args = new ContentValues();
        args.put( KEY_ITEM , item);
        args.put(KEY_AMOUNT, amount);
        args.put(KEY_UNIT, unit);
        args.put( KEY_CALORIE_VALUE , calorieValue);
    	openDB();        
        int erg = db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null);
        closeDB();
		return erg > 0;
    }
}
 
