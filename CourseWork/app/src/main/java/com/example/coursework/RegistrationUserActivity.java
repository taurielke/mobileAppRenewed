package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.user.User;
import com.example.coursework.user.UserData;

public class RegistrationUserActivity extends AppCompatActivity {
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        userData = new UserData(this);
        Button reg = findViewById(R.id.registrationButton);
        TextView login = findViewById(R.id.regEditTextLogin);
        TextView password = findViewById(R.id.regEditTextPassword);
        reg.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("") ||
                    log.length() < 5 || pas.length() < 5){
                Toast.makeText(this, "Insert at least 5 characters in each field",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                user.setRole("user");
                boolean ret = userData.registration(user);
                if (ret){
                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    Toast.makeText(this, "Successful sign in",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "User with this login already exists",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}