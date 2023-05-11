package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.user.User;
import com.example.coursework.user.UserData;

public class AuthorizationAdminActivity extends AppCompatActivity {

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_admin);

        userData = new UserData(this);

        Button aut = findViewById(R.id.btnAuth);
        TextView login = findViewById(R.id.textViewLogin);
        TextView password = findViewById(R.id.textViewPassword);

        aut.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("")){
                Toast.makeText(this, "Insert login and password",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                User ret = userData.authorization(user);
                if (ret == null){
                    Toast.makeText(this, "Incorrect login or password, or this user doesn't exist",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", ret.getLogin());
                    ed.putString("role", ret.getRole());
                    ed.commit();
                    Intent intent = new Intent(this, MainUserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}