package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coursework.user.BrushesData;
import com.example.coursework.user.Course;
import com.example.coursework.user.CoursesData;

import java.util.Objects;

public class CoursesActivity extends AppCompatActivity {

    String role = "";
    CoursesData coursesData;
    ArrayAdapter<Course> adapter;
    ListView listViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");

        coursesData = new CoursesData(this);

        listViewCourses = findViewById(R.id.listViewBrushes);
        Button add = findViewById(R.id.btnAdd);
        Button upd = findViewById(R.id.btnEdit);
        Button del = findViewById(R.id.btnDelete);
        Button disc = findViewById(R.id.btnDiscount);

        add.setVisibility(View.GONE);
        upd.setVisibility(View.GONE);
        del.setVisibility(View.GONE);

        if (Objects.equals(role, "admin")) {
            add.setVisibility(View.VISIBLE);
            upd.setVisibility(View.VISIBLE);
            del.setVisibility(View.VISIBLE);
        }

        adapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_single_choice,
                coursesData.findAllCourses());
        listViewCourses.setAdapter(adapter);
        listViewCourses.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

        disc.setOnClickListener(v -> {
            int course = -1;
            SparseBooleanArray sparseBooleanArray = listViewCourses.getCheckedItemPositions();
            for (int i = 0; i < listViewCourses.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    course = adapter.getItem(i).getId();
                }
            }
            if (course == -1){
                Toast.makeText(this, "Please choose an item",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int price = coursesData.getCourse(course).getPrice();
            Intent intent = new Intent(this, DiscountActivity.class);
            intent.putExtra("price", price);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewCourses.clearChoices();
        });

        upd.setOnClickListener(v -> {
            int course = -1;
            SparseBooleanArray sparseBooleanArray = listViewCourses.getCheckedItemPositions();
            for (int i = 0; i < listViewCourses.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    course = adapter.getItem(i).getId();
                }
            }
            if (course == -1){
                return;
            }
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra("Id", course);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewCourses.clearChoices();
        });

        del.setOnClickListener(v -> {
            int course = -1;
            SparseBooleanArray sparseBooleanArray = listViewCourses.getCheckedItemPositions();
            for (int i = 0; i < listViewCourses.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    course = adapter.getItem(i).getId();
                }
            }
            if (course != -1) {
                int finalCourse = course;
                coursesData.deleteCourse(finalCourse);
                listViewCourses.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}