package com.example.coursework.user;
import android.content.Context;

import com.example.coursework.user.DB.CoursesDB;

import java.util.ArrayList;
import java.util.List;

public class CoursesData {

    private static ArrayList<Course> courses  = new ArrayList<Course>();
    CoursesDB coursesDB;

    public CoursesData(Context context){
        coursesDB = new CoursesDB(context);
        readAll();
    }

    public Course getCourse(int id){
        Course tr = new Course();
        tr.setId(id);
        return coursesDB.get(tr);
    }

    public List<Course> findAllCourses(){
        return courses;
    }

    public void addCourse(String name, String description, int price){
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        coursesDB.add(course);
        readAll();
    }
    public void updateCourse(int id, String name, String description, int price){
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        coursesDB.update(course);
        readAll();
    }
    public void deleteCourse(int id){
        Course course = new Course();
        course.setId(id);
        coursesDB.delete(course);
        readAll();
    }

    private void readAll(){
        List<Course> brs = coursesDB.readAll();
        courses.clear();
        for(Course course : brs){
            courses.add(course);
        }
    }
}
