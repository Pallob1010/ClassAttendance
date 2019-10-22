package Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import Model.Constants;

public class SharedPreference {

    Context context;

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
