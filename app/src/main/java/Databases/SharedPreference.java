package Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.Constants;

public class SharedPreference {

    Context context;
    long times;
    SimpleDateFormat format;
    public SharedPreference(Context context) {
        this.context=context;
    }

    public void saveData(String name, String number,String password,String designation){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.NUMBER,number);
        editor.putString(Constants.PASSWORD,password);
        editor.putString(Constants.NAME,name);
        editor.putString(Constants.DESIGNATION,designation);
        editor.commit();

    }


    public void saveSynctime(String time){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SYNCTIME,time);
        editor.commit();

    }

    public void saveDay(String course,String series,String section){
        times= Calendar.getInstance().getTimeInMillis();
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(course+series+section+getNumber(),String.valueOf(times));
        editor.commit();
    }

    public void saveRunningCycle(String course,String series,String section,String cycle){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.CYCLE+course+series+section+getNumber(),cycle);
        editor.commit();
    }
    public String getRunningCycle(String course,String series,String section){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.CYCLE+course+series+section+getNumber(),""));
    }


    public String getDate(String course,String series,String section){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(course+series+section+getNumber(),"50000"));
    }

    public String getNumber(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.NUMBER,""));
    }

    public String getPassword(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.PASSWORD,""));
    }
    public String getUserName(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.NAME,""));
    }
    public String getUserDesignation(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.DESIGNATION,""));
    }
    public String getLastSyncTime(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.SYNCTIME,""));
    }

}
