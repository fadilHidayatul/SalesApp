package com.example.sales.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "sales";
    public static final String SESSION_KEY = "SESSION_USER";
    public static final String NAMA_SALES = "NAMA_SALES";
    public static final String ID_SALES =  "ID_SALES";
    public static final int ID_CABANG = 0;


    //constructor
    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //session
    public void saveSession(){
        editor.putBoolean(SESSION_KEY,true);
        editor.commit();
    }
    public boolean getSession(){
        return preferences.getBoolean(SESSION_KEY,false);
    }
    public void removeSession(){
        editor.putBoolean(SESSION_KEY,false);
        editor.commit();
    }

    //nama_sales
    public void setNamaSales(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getNamaSales(){
        return preferences.getString(NAMA_SALES,"");
    }

    //id_sales
    public void setIdSales(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getIdSales(){
        return preferences.getString(ID_SALES, "");
    }

    //id_cabang
    public void setIdCabang(int key, int value){
        editor.putInt(String.valueOf(key), value);
        editor.commit();
    }
    public int getIdCabang(){
        return preferences.getInt(String.valueOf(ID_CABANG),0);
    }



}
