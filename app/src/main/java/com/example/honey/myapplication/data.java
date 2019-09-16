package com.example.honey.myapplication;

/**
 * Created by HONEY on 2/3/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class data extends SQLiteOpenHelper {
private static final String database_name="mydata.db";
private static final int database_version=1;
private static final String table_name="mytable";
private static final String name="name";
private static final String email="Email_Id";
private static final String branch="Branch";
private static final String mob="Mobile";
private static final String bdate="Bday_date";
private static final String create_table="CREATE TABLE "+table_name+" ("+name+" VARCHAR(30) ,"+email+" VARCHAR(40) ," +mob+" VARCHAR(13) ,"+bdate+" VARCHAR(20) ,"+branch+" VARCHAR(15));";
private static final String drop_table="DROP TABLE IF EXIST "+table_name;

public data(Context context) {
    super(context, database_name, null, database_version);
    }
    public long insert(String sn, String sm, String se, String sd,String sb){
       SQLiteDatabase db=getWritableDatabase();
       ContentValues values=new ContentValues();
       values.put(name,sn);
       values.put(mob,sm);
       values.put(email,se);
       values.put(bdate,sd);
       values.put(branch,sb);
       long id=db.insert(table_name,null,values);
       return id;
    }
  public int delete(String sdel){
      String[] whereArgs={sdel};
        SQLiteDatabase db=getWritableDatabase();
      return db.delete(table_name,name+" =?",whereArgs);
  }
  public void deltable(){
      SQLiteDatabase db=getWritableDatabase();
      Log.d(TAG, "Reached to data class methods.... ");
      db.execSQL("delete from "+table_name);
  }
  public String viewdata(){
        SQLiteDatabase db=getReadableDatabase();
        String[] columns={name,mob,branch};
    Cursor cursor=db.query(table_name,columns,null,null,null,null,null);
    StringBuffer buffer=new StringBuffer();
    while(cursor.moveToNext()){
        String cname=cursor.getString(cursor.getColumnIndex(name));
        String cmob=cursor.getString(cursor.getColumnIndex(mob));
        String cbranch=cursor.getString(cursor.getColumnIndex(branch));
        buffer.append(cname+" "+cmob+" "+cbranch+"\n");
    }
return buffer.toString();
    }
    public void onCreate(SQLiteDatabase db){
    try{
        db.execSQL(create_table);
    }catch(Exception e){}
}
public void onUpgrade(SQLiteDatabase db,int oldver,int newver){
    try{
        db.execSQL(drop_table);
        onCreate(db);
    }catch (Exception e){}
}


}
