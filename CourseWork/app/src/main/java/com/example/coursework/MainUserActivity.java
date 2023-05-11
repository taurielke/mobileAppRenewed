package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainUserActivity extends AppCompatActivity {

    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);


        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");


        Button btnBrushes = findViewById(R.id.btnBrushes);
        Button btnPrints = findViewById(R.id.btnPrints);
        Button btnCourses = findViewById(R.id.btnCourses);
        Button btnReports = findViewById(R.id.btnReports);

        Button btnContact = findViewById(R.id.btnContact);

        btnReports.setVisibility(View.GONE);

        if (Objects.equals(role, "admin")) {
            btnReports.setVisibility(View.VISIBLE);
        }

        btnBrushes.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrushesActivity.class);
            startActivity(intent);
        });
        btnPrints.setOnClickListener(v -> {
            Intent intent = new Intent(this, PrintsActivity.class);
            startActivity(intent);
        });
        btnCourses.setOnClickListener(v -> {
            Intent intent = new Intent(this, CoursesActivity.class);
            startActivity(intent);
        });
        btnReports.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportUsersActivity.class);
            startActivity(intent);
        });
        btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        });
    }
}