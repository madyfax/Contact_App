package demo.contact.app.activity;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;


public class Myapp extends Application {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("my", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        database = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        database.execSQL("create table if not exists Reg(id integer primary key autoincrement,imagepath text,username text,mail text,num text,bdate text, adds text,password text)");
        database.execSQL("create table if not exists Contact(id integer primary key autoincrement,imagepath text, cname1 text, cnumber1 text, caddress1 text,uid text)");
        database.execSQL("create table if not exists favo(id integer primary key autoincrement, cid text,uid text)");

    }

    public static void setCID(String CID) {
        editor.putString("CID", CID).commit();
    }

    public static String getCID() {
        return sharedPreferences.getString("CID", "");
    }

    public static void SETUID(String UID) {
        editor.putString("UID", UID).commit();
    }

    public static String GETUID() {
        return sharedPreferences.getString("UID", "");
    }


    public static void setusername(String s) {
        editor.putString("hb", s).commit();
    }

    public static String getusername() {
        return sharedPreferences.getString("hb", "");
    }

    public static void setname(String s) {
        editor.putString("hh", s).commit();
    }

    public static String getname() {
        return sharedPreferences.getString("hh", "");
    }

    public static void setnumber(String t) {
        editor.putString("bb", t).commit();
    }

    public static String getnumber() {
        return sharedPreferences.getString("bb", "");
    }

    public static void setpass(String j) {
        editor.putString("pass", j).commit();
    }

    public static String getpass() {
        return sharedPreferences.getString("pass", "");
    }


    public static void SetVerify(boolean b) {
        editor.putBoolean("aa", b).commit();
    }

    public static boolean GetVerify() {
        return sharedPreferences.getBoolean("aa", false);
    }

    public static void setimage(String d) {
        editor.putString("ll", d).commit();
    }

    public static String getimage() {
        return sharedPreferences.getString("ll", "");
    }

    public static void setstatus(boolean b) {
        editor.putBoolean("aa", b).commit();
    }

    public static boolean getstatus() {
        return sharedPreferences.getBoolean("aa", false);
    }


}

