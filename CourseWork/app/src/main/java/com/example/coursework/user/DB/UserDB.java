package com.example.coursework.user.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDB {

    private UserDB.DBHelper dbHelper;

    public UserDB(Context context){
        dbHelper = new UserDB.DBHelper(context);
    }

    public boolean registration(User user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { user.getLogin() };
        Cursor c = db.query("users", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                dbHelper.close();
                return false;
            }
            ContentValues cv = new ContentValues();
            cv.put("login", user.getLogin());
            cv.put("password", user.getPassword());
            cv.put("role", user.getRole());
            long userId =  db.insert("users", null, cv);
            dbHelper.close();
            return true;
        }
        dbHelper.close();
        return false;
    }

    public User authorization(User user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { user.getLogin() };
        Cursor c = db.query("users", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                if (Objects.equals(user.getLogin(), c.getString(1)) &&
                        Objects.equals(user.getPassword(), c.getString(2))){
                    User userr = new User();
                    userr.setLogin(user.getLogin());
                    userr.setPassword(user.getPassword());
                    userr.setRole(c.getString(3));
                    c.close();
                    db.close();
                    return userr;
                }
            }
            c.close();
            dbHelper.close();
            return null;
        }
        dbHelper.close();
        return null;
    }

    public List<User> readAll(User user) {
        if (!Objects.equals(user.getRole(), "admin")) {
            return null;
        }
        List<User> retList = new ArrayList<User>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("users", null, null,
                null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex("id");
            int logIndex = c.getColumnIndex("login");
            int passIndex = c.getColumnIndex("password");
            int rolIndex = c.getColumnIndex("role");
            do {
                User usr = new User();
                usr.setId(c.getInt(idIndex));
                usr.setRole(c.getString(rolIndex));
                usr.setLogin(c.getString(logIndex));
                usr.setPassword(c.getString(passIndex));
                retList.add(usr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "userqqq", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table users ("
                    + "id integer primary key autoincrement,"
                    + "login text,"
                    + "password text,"
                    + "role text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
