package com.example.coursework.user;
import android.content.Context;

import com.example.coursework.user.DB.BrushesDB;

import java.util.ArrayList;
import java.util.List;

public class BrushesData {

    private static ArrayList<Brush> brushes  = new ArrayList<Brush>();
    BrushesDB brushesDB;

    public BrushesData(Context context){
        brushesDB = new BrushesDB(context);
        readAll();
    }

    public Brush getBrush(int id){
        Brush tr = new Brush();
        tr.setId(id);
        return brushesDB.get(tr);
    }

    public List<Brush> findAllBrushes(){
        return brushes;
    }

    public void addBrush(String name, String description, int price){
        Brush brush = new Brush();
        brush.setName(name);
        brush.setDescription(description);
        brush.setPrice(price);
        brushesDB.add(brush);
        readAll();
    }
    public void updateBrush(int id, String name, String description, int price){
        Brush brush = new Brush();
        brush.setId(id);
        brush.setName(name);
        brush.setDescription(description);
        brush.setPrice(price);
        brushesDB.update(brush);
        readAll();
    }
    public void deleteBrush(int id){
        Brush brush = new Brush();
        brush.setId(id);
        brushesDB.delete(brush);
        readAll();
    }
    private void readAll(){
        List<Brush> brs = brushesDB.readAll();
        brushes.clear();
        for(Brush brush : brs){
            brushes.add(brush);
        }
    }
}
