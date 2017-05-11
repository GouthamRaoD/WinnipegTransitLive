package com.youngbro.mytransitlive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorites.db";
    private static final String TABLE_NAME = "stops";
    private static final String COLUMN_NUM = "stopno";
    private SQLiteDatabase db;
    private static final String COLUMN_NAME = "stopname";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NUM + " TEXT," +
                    COLUMN_NAME + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DataBase(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase d) {
        this.db = d;
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase d, int oldVersion, int newVersion) {
        this.db = d;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    void addStop(String name, String stopNo){
        if(!isPresent(stopNo)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NUM, stopNo);
            values.put(COLUMN_NAME, name);
            db = getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    public void deleteStop(String stopNo)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME +" WHERE "+COLUMN_NUM+ " =\""+stopNo+"\";");
    }

    public ArrayList<DbData> getData()
    {
        ArrayList<DbData> data = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE 1";

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NUM));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME));
                data.add(new DbData(name,itemId));
        }
        cursor.close();
        db.close();
        return data;
    }
    public boolean isPresent(String stop){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE 1";

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()) {
            if(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NUM)).equals(stop)){
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }
}
