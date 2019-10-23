package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class DatabaseCday extends SQLiteOpenHelper {
    public static final String DatabaseName = "Databasecday.db";
    public static final String TableName = "TableC";
    public static final String COL_1 = "ID";//included -course,series,section,number;
    public static final String COL_2 = "Roll";
    public static final String COL_3 = "Cycle";
    public static final String COL_4 = "Day";
    public static final String COL_5 = "State";
    public static final String COL_6 = "Count";
    Context context;
    SharedPreference preference;

    public DatabaseCday(Context context) {
        super(context, DatabaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
        preference=new SharedPreference(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableName + " (ID TEXT,Roll TEXT,Cycle TEXT,Day TEXT,State TEXT,Count TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }


    public int inserts(String course, String series, String section, String cycle, String day, String roll, String state,int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, course+series+section+preference.getNumber());
        contentValues.put(COL_2, roll);
        contentValues.put(COL_3,cycle);
        contentValues.put(COL_4,day);
        contentValues.put(COL_5,state);
        contentValues.put(COL_6,String.valueOf(count));
        long result = db.insert(TableName, null, contentValues);

        return 1;
    }


    public void updateState(String course, String series, String section, String cycle, String day, String roll, String state) {

        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "UPDATE " + TableName + " SET State =" + "'" + state + "'" + " WHERE ID= " + "'" + course+series+section+preference.getNumber()+ "'"+ " AND Roll= " + "'" + roll + "'"+" AND Day= " + "'" + day + "'"+" AND Cycle= " + "'" + cycle + "'" ;
        db.execSQL(SQL);


    }



    public ArrayList<String> getRoll(String series, String section, String course, String cycle,String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        String SQL = "SELECT Roll FROM " + TableName + " WHERE ID = " + "'" + course+series+section+preference.getNumber()+"'"+" AND Cycle= " + "'" + cycle+ "'"+ " AND Day='"+day+"'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));

        }

        return list;


    }


    public ArrayList<String> getState(String series, String section, String course, String cycle,String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String>ob=new ArrayList<>();
        String SQL = "SELECT State FROM " + TableName + " WHERE ID = " + "'" + course+series+section+preference.getNumber()+"'"+" AND Cycle= " + "'" + cycle+ "'"+ " AND Day='"+day+"'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            ob.add(cursor.getString(0));
        }

        return ob;


    }

    public JsonArray getAllRoll() {
        SQLiteDatabase db = this.getWritableDatabase();
        JsonArray jsonArray=new JsonArray();
        String SQL = "SELECT Roll FROM " + TableName ;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            jsonArray.add(cursor.getString(0));
        }

        return jsonArray;

    }


    public JsonArray getAllState() {
        SQLiteDatabase db = this.getWritableDatabase();
        JsonArray jsonArray=new JsonArray();
        String SQL = "SELECT State FROM " + TableName ;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            jsonArray.add(cursor.getString(0));
        }

        return jsonArray;

    }
    public JsonArray getAllCycle() {
        SQLiteDatabase db = this.getWritableDatabase();
        JsonArray jsonArray=new JsonArray();
        String SQL = "SELECT Cycle FROM " + TableName ;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            jsonArray.add(cursor.getString(0));
        }

        return jsonArray;

    }



    public JsonArray getAllId() {
        SQLiteDatabase db = this.getWritableDatabase();
        JsonArray jsonArray=new JsonArray();
        String SQL = "SELECT ID FROM " + TableName ;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            jsonArray.add(cursor.getString(0));
        }

        return jsonArray;
    }


    public void DeleteByCSS(String course, String series, String section) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DELETE FROM " + TableName + " WHERE ID LIKE  " + "'" + course+series+section+preference.getNumber()+"%" + "'";

        db.execSQL(SQL);

    }


    public void deleteByRoll(String series, String section,String course, String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DELETE FROM " + TableName +  " WHERE ID LIKE  " + "'" + course+series+section+preference.getNumber()+"%" + "'"+" AND Roll= "+"'"+roll+"'";
        db.execSQL(SQL);

    }

    public void update(String course,String series,String section,String cycle,String day){

        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "UPDATE " + TableName + " SET Count ='1'"+" WHERE ID= " + "'" + course+series+section+preference.getNumber()+ "'"+" AND Day= " + "'" + day + "'"+" AND Cycle= " + "'" + cycle + "'" ;
        db.execSQL(SQL);
    }


    public String getTotalcount(String course, String series, String section, String roll, String cycles,String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        String count="";
        String SQL = "SELECT Count FROM " + TableName +" WHERE ID = " + "'" + course+series+section+preference.getNumber()+"'"+" AND Cycle= " + "'" + cycles+ "'"+ " AND Day='"+day+"'"+" AND Roll='"+roll+"'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            count=cursor.getString(0);
        }



        return count;

    }

    public int getProg(String course, String series, String section,String cycles) {
        SQLiteDatabase db = this.getWritableDatabase();
        String count="0";
        String SQL = "SELECT Count FROM " + TableName +" WHERE ID = " + "'" + course+series+section+preference.getNumber()+"'"+" AND Cycle= " + "'" + cycles+ "'"+"AND Day='C'";
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToNext()){
            count=cursor.getString(0);
        }

        return Integer.parseInt(count);

    }
}


