package com.example.coursework.user.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.user.User;
import com.example.coursework.user.Brush;

import java.util.ArrayList;
import java.util.List;

public class BrushesDB {
    
    private BrushesDB.DBHelper dbHelper;

    public BrushesDB(Context context){
        dbHelper = new BrushesDB.DBHelper(context);
    }

    public Brush get(Brush brush){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("brushes", null, "id = ?",
                new String[] {String.valueOf(brush.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int descriptionIndex = c.getColumnIndex("description");
            int priceIndex = c.getColumnIndex("price");
            Brush tr = new Brush();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setDescription(c.getString(descriptionIndex));
            tr.setPrice(c.getInt(priceIndex));
            if (tr.getId() == (brush.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Brush brush){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", brush.getName());
        cv.put("description", brush.getDescription());
        cv.put("price", brush.getPrice());
        long brushId = db.insert("brushes", null, cv);
        dbHelper.close();
    }

    public void update(Brush brush){
        if (get(brush) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", brush.getName());
        cv.put("description", brush.getDescription());
        cv.put("price", brush.getPrice());
        db.update("brushes", cv, "id = ?", new String[] {String.valueOf(brush.getId())});
        dbHelper.close();
    }

    public void delete(Brush brush){
        if(get(brush) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("brushes", "id = " + brush.getId(), null);
        dbHelper.close();
    }

    public List<Brush> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Brush> brushList = new ArrayList<Brush>();
        Cursor c = db.query("brushes", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int descriptionIndex = c.getColumnIndex("description");
            int priceIndex = c.getColumnIndex("price");
            do{
                Brush tr = new Brush();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setDescription(c.getString(descriptionIndex));
                tr.setPrice(c.getInt(priceIndex));
                brushList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return brushList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "brushesqqq", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table brushes ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "description text,"
                    +  "price numeric" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    
}
