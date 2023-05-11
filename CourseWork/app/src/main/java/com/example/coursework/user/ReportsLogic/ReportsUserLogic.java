package com.example.coursework.user.ReportsLogic;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.coursework.user.User;
import com.example.coursework.user.UserData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsUserLogic {
    UserData userData;

    public ReportsUserLogic(Context context, String userLogin){
        userData = new UserData(context);
    }

    public List<AllUsersUnit> getAllUsersData(String login, String role, String password){
        List<User> users = userData.readAll(login, role, password);
        List<AllUsersUnit> userrList = new ArrayList<AllUsersUnit>();
        for (User userr : users){
            AllUsersUnit userUnit = new AllUsersUnit();
            userUnit.setLogin(userr.getLogin());
            userUnit.setPassword(userr.getPassword());
            userUnit.setRole(userr.getRole());
            userrList.add(userUnit);
        }
        return userrList;
    }


}
