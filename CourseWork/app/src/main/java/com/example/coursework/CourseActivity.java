package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coursework.user.Course;
import com.example.coursework.user.CoursesData;

public class CourseActivity extends AppCompatActivity {

    int id = -1;
    CoursesData coursesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        coursesData = new CoursesData(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.btnSave);
        TextView textViewName = findViewById(R.id.textViewNamePrint);
        TextView textViewDescription = findViewById(R.id.textViewSizePrint);
        TextView textViewPrice = findViewById(R.id.textViewPricePrint);

        if (id != -1){
            Course course = coursesData.getCourse(id);
            if (course != null){
                textViewName.setText(course.getName());
                textViewDescription.setText(course.getDescription());
                textViewPrice.setText(String.valueOf(course.getPrice()));
            }
        }

        save.setOnClickListener(v -> {
            if (textViewName.getText().equals("")){
                Toast.makeText(this, "Insert a name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewDescription.getText().equals("")){
                Toast.makeText(this, "Insert a description",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewPrice.getText().equals("")){
                Toast.makeText(this, "Insert a price",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String name = textViewName.getText().toString();
            String description = textViewDescription.getText().toString();
            int price = Integer.parseInt(textViewPrice.getText().toString());
            if (id != -1){
                coursesData.updateCourse(id, name, description, price);
            }
            else {
                coursesData.addCourse(name, description, price);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}