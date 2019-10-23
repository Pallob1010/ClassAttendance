package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.JsonArray;

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
    public static final String col_7="Number";
    public static final String col_8="Aday";
    public static final String col_9="Bday";
    public static final String col_10="Cday";
    public static final String col_11="Dday";
    public static final String col_12="Eday";

    Context context;
    SharedPreference preference;
    public Information(Context context) {
        super(context, DatabaseName,null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
        this.context=context;
        preference=new SharedPreference(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TableName+ " (Course TEXT, Series TEXT,Section TEXT,TotalStudents TEXT,FirstRoll TEXT,Marks TEXT,Number TEXT,Aday TEXT,Bday TEXT,Cday TEXT,Dday TEXT,Eday Text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TableName);
            onCreate(db);
    }

    public boolean insertValues(String series,String section,String course,String firstroll,String toatalstudents,String marks,String number,String aday,String bday,String cday,String dday ,String eday){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(col_1,course);
        contentValues.put(col_2,series);
        contentValues.put(col_3,section);
        contentValues.put(col_4,toatalstudents);
        contentValues.put(col_5,firstroll);
        contentValues.put(col_6,marks);
        contentValues.put(col_7,number);
        contentValues.put(col_8,aday);
        contentValues.put(col_9,bday);
        contentValues.put(col_10,cday);
        contentValues.put(col_11,dday);
        contentValues.put(col_12,eday);
        long result=db.insert(TableName,null,contentValues);

        if(result==-1){

            return false;
        }else{
            return true;
        }


    }
    public String getAday(String course,String series,String section,String number){
        SQLiteDatabase db=this.getWritableDatabase();
        String s="";
        String SQL="SELECT  Aday FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'"+" AND Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s=cursor.getString(0);
        }
     return s;
    }
    public String getBday(String course,String series,String section,String number){
        SQLiteDatabase db=this.getWritableDatabase();
        String s="";
        String SQL="SELECT  Bday FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'"+" AND Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s=cursor.getString(0);
        }
        return s;
    }
    public String getCday(String course,String series,String section,String number){
        SQLiteDatabase db=this.getWritableDatabase();
        String s="";
        String SQL="SELECT  Cday FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'"+" AND Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s=cursor.getString(0);
        }
        return s;
    }
    public String getDday(String course,String series,String section,String number){
        SQLiteDatabase db=this.getWritableDatabase();
        String s="";
        String SQL="SELECT  Dday FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'"+" AND Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s=cursor.getString(0);
        }
        return s;
    }

    public String getEday(String course,String series,String section,String number){
        SQLiteDatabase db=this.getWritableDatabase();
        String s="";
        String SQL="SELECT  Eday FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'"+" AND Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            s=cursor.getString(0);
        }
        return s;
    }

    public int activeDay(String course,String series,String section){

        int counter=0;
        if (getAday(course,series,section,preference.getNumber()).equals("true")){
            counter++;
        }
        if (getBday(course,series,section,preference.getNumber()).equals("true")){
            counter++;
        }
        if (getCday(course,series,section,preference.getNumber()).equals("true")){
            counter++;
        } if (getDday(course,series,section,preference.getNumber()).equals("true")){
            counter++;
        }
        if (getEday(course,series,section,preference.getNumber()).equals("true")){
            counter++;
        }

        return counter;
    }



    public void DeleteSingle(String course,String series,String section){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="DELETE FROM "+TableName+ " WHERE Course = "+"'"+course+"'"+"AND Series = "+"'"+series+"'"+"AND Section = "+"'"+section+"'";
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

    public ArrayList<CourseDetails> getInformation(String number){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<CourseDetails>object=new ArrayList<>();

        String SQL="SELECT Course,Series,Section,Marks FROM "+TableName+" WHERE Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);
        while (cursor.moveToNext()) {
            object.add(new CourseDetails(cursor.getString(0),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3))));
        }
        return object;
    }

    public boolean hasData(){

        String countQuery = "SELECT  * FROM " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        if(count!=0){
            return true;
        }else {
            return false;
        }
    }

    public JsonArray getinfo(){
        JsonArray jsonArray=new JsonArray();
        JsonArray number=new JsonArray();
        JsonArray course=new JsonArray();
        JsonArray series=new JsonArray();
        JsonArray section=new JsonArray();
        JsonArray firstroll=new JsonArray();
        JsonArray totalstudent=new JsonArray();
        JsonArray marks=new JsonArray();
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL="SELECT * FROM "+TableName;
        number.add(preference.getNumber());
        Cursor cursor=db.rawQuery(SQL,null);

        while (cursor.moveToNext()){
            course.add(cursor.getString(0));
            series.add(cursor.getString(1));
            section.add(cursor.getString(2));
            totalstudent.add(cursor.getString(3));
            firstroll.add(cursor.getString(4));
            marks.add(cursor.getString(5));
        }

        jsonArray.add(number);
        jsonArray.add(course);
        jsonArray.add(series);
        jsonArray.add(section);
        jsonArray.add(totalstudent);
        jsonArray.add(firstroll);
        jsonArray.add(marks);

        return jsonArray;

    }






}
