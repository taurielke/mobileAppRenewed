package com.example.coursework.user.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.user.User;
import com.example.coursework.user.Course;

import java.util.ArrayList;
import java.util.List;

public class CoursesDB {

    private CoursesDB.DBHelper dbHelper;

    public CoursesDB(Context context){
        dbHelper = new CoursesDB.DBHelper(context);
    }

    public Course get(Course course){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("courses", null, "id = ?",
                new String[] {String.valueOf(course.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int descriptionIndex = c.getColumnIndex("description");
            int priceIndex = c.getColumnIndex("price");
            Course tr = new Course();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setDescription(c.getString(descriptionIndex));
            tr.setPrice(c.getInt(priceIndex));
            if (tr.getId() == (course.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Course course){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", course.getName());
        cv.put("description", course.getDescription());
        cv.put("price", course.getPrice());
        long courseId = db.insert("courses", null, cv);
        dbHelper.close();
    }

    public void update(Course course){
        if (get(course) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", course.getName());
        cv.put("description", course.getDescription());
        cv.put("price", course.getPrice());
        db.update("courses", cv, "id = ?", new String[] {String.valueOf(course.getId())});
        dbHelper.close();
    }

    public void delete(Course course){
        if(get(course) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("courses", "id = " + course.getId(), null);
        dbHelper.close();
    }

    public List<Course> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Course> courseList = new ArrayList<Course>();
        Cursor c = db.query("courses", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int descriptionIndex = c.getColumnIndex("description");
            int priceIndex = c.getColumnIndex("price");
            do{
                Course tr = new Course();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setDescription(c.getString(descriptionIndex));
                tr.setPrice(c.getInt(priceIndex));
                courseList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return courseList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "coursesqqq", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table courses ("
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
