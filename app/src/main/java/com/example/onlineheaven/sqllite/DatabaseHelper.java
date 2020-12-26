package com.example.onlineheaven.sqllite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onlineheaven.model.History;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper implements Serializable {

    public static final String DATABASE_NAME = "HistoryDatabase";
    public static final String HISTORY_TABLE_NAME = "UserSeriesHistory";
    private SQLiteDatabase mDb;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(
                    "create table "+ HISTORY_TABLE_NAME +"(id INTEGER PRIMARY KEY, userid INTEGER,seriename text,datetime default current_timestamp )"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public SQLiteDatabase open() {
       mDb = this.getWritableDatabase();
        return mDb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ HISTORY_TABLE_NAME);
        onCreate(db);
    }

    public long insert(Integer userid,String seriename) {
        mDb = this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("seriename", seriename);
        long i = mDb.insert(HISTORY_TABLE_NAME, null, contentValues);
        return i;
    }

    public ArrayList<History> getAllHistory(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<History> array_list = new ArrayList<History>();
        Cursor res = db.rawQuery( "select * from "+ HISTORY_TABLE_NAME +" WHERE userid ="+id+" ORDER BY datetime DESC ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            History h=new History(res.getInt(res.getColumnIndex("id")),res.getString(res.getColumnIndex("seriename"))+"   "+res.getString(res.getColumnIndex("datetime")));
            array_list.add(h);
            res.moveToNext();
        }
        return (ArrayList<History>) array_list;
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+ HISTORY_TABLE_NAME  );
        return true;
    }

    public boolean deleteSingle(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+ HISTORY_TABLE_NAME + " WHERE id = "+id);
        return true;
    }
}