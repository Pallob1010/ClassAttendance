package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DayDatabase extends SQLiteOpenHelper {
    public static final String DatabaseName = "Databaseaday.db";
    public static final String TableName = "MyDatabaseTable";
    public static final String COL_1 = "Id";//included -course,series,section,cycle,day;
    public static final String COL_2 = "Roll";
    public static final String COL_3 = "State";
    Context context;


    public DayDatabase(Context context) {
        super(context, DatabaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableName + " (Id TEXT,Roll TEXT,State TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    public int insertRollState( String course_series_section_cycle_day,String roll,String state ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,course_series_section_cycle_day);
        contentValues.put(COL_2,roll);
        contentValues.put(COL_3,state);
        long result = db.insert(TableName, null, contentValues);
        return 1;
    }

    public void updateState(String course_series_section_cycle_day,String roll,String state) {

        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "UPDATE " + TableName + " SET State="+"'"+state+"'"+" WHERE Id="+"'"+course_series_section_cycle_day+"'"+" AND Roll="+"'"+roll+ "'";
        db.execSQL(SQL);
    }

    public ArrayList<String> getRoll(String course_series_section_cycle_day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        String SQL = "SELECT Roll FROM " + TableName + " WHERE Id ="+"'"+course_series_section_cycle_day+"'";
        Cursor cursor = db.rawQuery(SQL, null);

        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));

        }
        return list;
    }

    public ArrayList<String> getState(String course_series_section_cycle_day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String>ob=new ArrayList<>();
        String SQL = "SELECT State FROM " + TableName + " WHERE Id="+"'"+course_series_section_cycle_day+"'";
        Cursor cursor = db.rawQuery(SQL, null);

        while (cursor.moveToNext()) {
            ob.add(cursor.getString(0));

        }
        return ob;
    }

    public void DeleteByCSS(String course_series_section) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DELETE FROM " + TableName + " WHERE Id = "+ "'" + course_series_section+ "'";

        db.execSQL(SQL);
    }
    public void deleteByRoll(String course_series_section_cycle_day,String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DELETE FROM "+TableName + " WHERE Id ="+"'"+course_series_section_cycle_day+"'"+" AND Roll="+"'"+roll+"'" ;
        db.execSQL(SQL);

    }
}
