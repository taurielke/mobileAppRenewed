package com.example.coursework.user.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.user.Print;

import java.util.ArrayList;
import java.util.List;

public class PrintsDB {

    private PrintsDB.DBHelper dbHelper;

    public PrintsDB(Context context){
        dbHelper = new PrintsDB.DBHelper(context);
    }

    public Print get(Print print){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("prints", null, "id = ?",
                new String[] {String.valueOf(print.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int sizeIndex = c.getColumnIndex("size");
            int priceIndex = c.getColumnIndex("price");
            Print tr = new Print();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setSize(c.getString(sizeIndex));
            tr.setPrice(c.getInt(priceIndex));
            if (tr.getId() == (print.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Print print){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", print.getName());
        cv.put("size", print.getSize());
        cv.put("price", print.getPrice());
        long printId = db.insert("prints", null, cv);
        dbHelper.close();
    }

    public void update(Print print){
        if (get(print) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", print.getName());
        cv.put("size", print.getSize());
        cv.put("price", print.getPrice());
        db.update("prints", cv, "id = ?", new String[] {String.valueOf(print.getId())});
        dbHelper.close();
    }

    public void delete(Print print){
        if(get(print) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("prints", "id = " + print.getId(), null);
        dbHelper.close();
    }

    public List<Print> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Print> printList = new ArrayList<Print>();
        Cursor c = db.query("prints", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int sizeIndex = c.getColumnIndex("size");
            int priceIndex = c.getColumnIndex("price");
            do{
                Print tr = new Print();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setSize(c.getString(sizeIndex));
                tr.setPrice(c.getInt(priceIndex));
                printList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return printList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "printsqqq", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table prints ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "size text,"
                    +  "price numeric" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
