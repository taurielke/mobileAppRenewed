package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionAskedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_asked);

        Button yes = findViewById(R.id.btnYes);
        Button no = findViewById(R.id.btnNo);

        yes.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthorizationAdminActivity.class);
            startActivity(intent);
        });

        no.setOnClickListener(v -> {
            Intent intent = new Intent(this, AutorizationUserActivity.class);
            startActivity(intent);
        });
    }
}