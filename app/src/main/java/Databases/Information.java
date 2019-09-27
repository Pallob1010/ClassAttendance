package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Model.CourseDetails;


public class Information extends SQLiteOpenHelper {

    public static final String DatabaseName="Information.db";
    public static final String TableName="MyTable";
    public static final String col_1="Course";
    public static final String col_2="Series";
    public static final String col_3="Section";
    public static final String col_4="TotalStudents";
    public static final String col_5="FirstRoll";
    public static final String col_6="Marks";




    Context context;

    public Information(Context context) {
        super(context, DatabaseName,null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TableName+ " (Course TEXT, Series TEXT,Section TEXT,TotalStudents TEXT,FirstRoll TEXT,Marks TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TableName);
            onCreate(db);
    }

    public boolean insertValues(String series,String section,String course,String firstroll,String toatalstudents,String marks ){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(col_1,course);
        contentValues.put(col_2,series);
        contentValues.put(col_3,section);
        contentValues.put(col_4,toatalstudents);
        contentValues.put(col_5,firstroll);
        contentValues.put(col_6,marks);
        long result=db.insert(TableName,null,contentValues);

        if(result==-1){

            return false;
        }else{
            return true;
        }


    }


    public void DeleteSingle(String course,String series,String section){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="DELETE FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'";
        db.execSQL(SQL);

    }




    public void UpdateState(String course,String series,String section,String cycle,String day,String roll,String state){

        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="UPDATE "+TableName+" SET State ="+"'"+state+"'"+" WHERE Course= "+"'"+course+"'"+" AND Series= "
                +"'"+series+"'"+" AND Section= " +"'"+section+"'"+" AND Cycle= "+"'"+cycle+"'"+" AND Day= "+"'"+day+"'"
                +" AND Roll= "+"'"+roll+"'";
        db.execSQL(SQL);


    }

    public ArrayList<String> getCourse(){
        ArrayList<String>s=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="SELECT Course FROM "+TableName;
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s.add(cursor.getString(0));

        }

        Set<String> duplicate = new HashSet<>();
        duplicate.addAll(s);
        s.clear();
        s.addAll(duplicate);
        return s;
    }
    public ArrayList<String> getSeries(){
        ArrayList<String>s=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="SELECT Series FROM "+TableName;
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s.add(cursor.getString(0));

        }

        Set<String> duplicate = new HashSet<>();
        duplicate.addAll(s);
        s.clear();
        s.addAll(duplicate);
        return s;
    }

    public ArrayList<String> getSection(){
        ArrayList<String>s=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="SELECT Section FROM "+TableName;
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s.add(cursor.getString(0));

        }

        Set<String> duplicate = new HashSet<>();
        duplicate.addAll(s);
        s.clear();
        s.addAll(duplicate);
        return s;
    }

    public ArrayList<CourseDetails> getInformation(){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<CourseDetails>object=new ArrayList<>();
        String SQL="SELECT Course,Series,Section FROM "+TableName;
        Cursor cursor=db.rawQuery(SQL,null);
        while (cursor.moveToNext()) {
            object.add(new CourseDetails(cursor.getString(0),cursor.getString(1),cursor.getString(2)));

        }
        return object;
    }




}
