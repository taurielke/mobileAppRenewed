package com.example.coursework.user;
import android.content.Context;

import com.example.coursework.user.DB.PrintsDB;

import java.util.ArrayList;
import java.util.List;

public class PrintsData {

    private static ArrayList<Print> prints  = new ArrayList<Print>();
    PrintsDB printsDB;

    public PrintsData(Context context){
        printsDB = new PrintsDB(context);
        readAll();
    }

    public Print getPrint(int id){
        Print tr = new Print();
        tr.setId(id);
        return printsDB.get(tr);
    }

    public List<Print> findAllPrints(){
        return prints;
    }

    public void addPrint(String name, String size, int price){
        Print print = new Print();
        print.setName(name);
        print.setSize(size);
        print.setPrice(price);
        printsDB.add(print);
        readAll();
    }
    public void updatePrint(int id, String name, String size, int price){
        Print print = new Print();
        print.setId(id);
        print.setName(name);
        print.setSize(size);
        print.setPrice(price);
        printsDB.update(print);
        readAll();
    }
    public void deletePrint(int id){
        Print print = new Print();
        print.setId(id);
        printsDB.delete(print);
        readAll();
    }

    private void readAll(){
        List<Print> brs = printsDB.readAll();
        prints.clear();
        for(Print print : brs){
            prints.add(print);
        }
    }
}
