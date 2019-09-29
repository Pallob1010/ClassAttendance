package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseDday extends SQLiteOpenHelper {
    public static final String DatabaseName="Databasedday.db";
    public static final String TableName="TableD";
    Context context;
    public static final String col_1="Course";
    public static final String col_2="Series";
    public static final String col_3="Section";
    public static final String COL_4="Cycle";
    public static final String COL_5="Day";
    public static final String COL_6="Roll";
    public static final String COL_7="State";

    public DatabaseDday(Context context) {
        super(context,DatabaseName,null,1);
        SQLiteDatabase db=this.getWritableDatabase();
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TableName+ " (Course TEXT, Series TEXT,Section TEXT,Cycle TEXT,Day TEXT,Roll TEXT,State TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }


    public int insertRollStateDDay(String course,String series,String section,String cycle,String day,String roll,String state){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(col_1,course);
        contentValues.put(col_2,series);
        contentValues.put(col_3,section);
        contentValues.put(COL_4,cycle);
        contentValues.put(COL_5,day);
        contentValues.put(COL_6,roll);
        contentValues.put(COL_7,state);

        long result=db.insert(TableName,null,contentValues);

        return 1;
    }

    public void UpdateStateDday(String course,String series,String section,String cycle,String day,String roll,String state){

        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="UPDATE "+TableName+" SET State ="+"'"+state+"'"+" WHERE Course= "+"'"+course+"'"+" AND Series= "
                +"'"+series+"'"+" AND Section= " +"'"+section+"'"+" AND Cycle= "+"'"+cycle+"'"+" AND Day= "+"'"+day+"'"
                +" AND Roll= "+"'"+roll+"'";
        db.execSQL(SQL);


    }

    public Cursor GetEverythingday(String course,  String section, String cycle, String day,String series){
        SQLiteDatabase db=this.getWritableDatabase();

        String SQL="SELECT * FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Section = "+"'"+section+"'" +" AND Cycle = "+"'"+cycle+"'" +" AND Day = "+"'"+day+"'"+" AND Series="+"'"+series+"'";
        Cursor cursor=db.rawQuery(SQL,null);
        return cursor;


    }
    public Cursor GetStateDday(String course,String series,String section,String cycle,String day){
        SQLiteDatabase db=this.getWritableDatabase();

        String SQL="SELECT State FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Series = "+"'"+series+"'"+" AND Section = "+"'"+section+"'" +" AND Cycle = "+"'"+cycle+"'" +" AND Day = "+"'"+day+"'" ;
        Cursor cursor=db.rawQuery(SQL,null);
        return cursor;


    }

    public Cursor GetRollStateDday(String course,String series,String section,String cycle){
        SQLiteDatabase db=this.getWritableDatabase();

        String SQL="SELECT State FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Series = "+"'"+series+"'"+" AND Section = "+"'"+section+"'" +" AND Cycle = "+"'"+cycle+"'" +" AND Day = "+"'"+"D"+"'" ;
        Cursor cursor=db.rawQuery(SQL,null);
        return cursor;

    }

    public Cursor GetStateExtra(String course,String series,String section,String cycle,String roll){
        SQLiteDatabase db=this.getWritableDatabase();

        String SQL="SELECT State FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Series = "+"'"+series+"'"+" AND Section = "+"'"+section+"'" +" AND Cycle = "+"'"+cycle+"'" +" AND Day = "+"'"+"D"+"'"+" AND Roll="+"'"+roll+"'";
        Cursor cursor=db.rawQuery(SQL,null);
        return cursor;


    }


    public Cursor GetAll(String course, String series, String section){
        SQLiteDatabase db=this.getWritableDatabase();

        String SQL="SELECT * FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Series = "+"'"+series+"'"+" AND Section = "+"'"+section+"'";
        Cursor cursor=db.rawQuery(SQL,null);
        return cursor;


    }
    public void DeleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TableName);

    }
    public void DeleteByCSS(String course,String series,String section){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="DELETE FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Series = "+"'"+series+"'"+" AND Section = "+"'"+section+"'";

        db.execSQL(SQL);

    }
    public void DeleteByCS(String course,String section,String roll){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="DELETE FROM "+TableName+" WHERE Course = "+"'"+course+"'"+" AND Roll = "+"'"+roll+"'"+" AND Section = "+"'"+section+"'";

        db.execSQL(SQL);

    }

}
