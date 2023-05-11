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

import com.example.coursework.user.Brush;
import com.example.coursework.user.BrushesData;

import java.util.Objects;

public class BrushesActivity extends AppCompatActivity {

    String role = "";
    BrushesData brushesData;
    ArrayAdapter<Brush> adapter;
    ListView listViewBrushes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brushes);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");

        brushesData = new BrushesData(this);

        listViewBrushes = findViewById(R.id.listViewBrushes);

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

        adapter = new ArrayAdapter<Brush>(this, android.R.layout.simple_list_item_single_choice,
                brushesData.findAllBrushes());
        listViewBrushes.setAdapter(adapter);
        listViewBrushes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrushActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

        disc.setOnClickListener(v -> {
            int brush = -1;
            SparseBooleanArray sparseBooleanArray = listViewBrushes.getCheckedItemPositions();
            for (int i = 0; i < listViewBrushes.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    brush = adapter.getItem(i).getId();
                }
            }
            if (brush == -1){
                Toast.makeText(this, "Please choose an item",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int price = brushesData.getBrush(brush).getPrice();
            Intent intent = new Intent(this, DiscountActivity.class);
            intent.putExtra("price", price);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewBrushes.clearChoices();
        });

        upd.setOnClickListener(v -> {
            int brush = -1;
            SparseBooleanArray sparseBooleanArray = listViewBrushes.getCheckedItemPositions();
            for (int i = 0; i < listViewBrushes.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    brush = adapter.getItem(i).getId();
                }
            }
            if (brush == -1){
                return;
            }
            Intent intent = new Intent(this, BrushActivity.class);
            intent.putExtra("Id", brush);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewBrushes.clearChoices();
        });

        del.setOnClickListener(v -> {
            int brush = -1;
            SparseBooleanArray sparseBooleanArray = listViewBrushes.getCheckedItemPositions();
            for (int i = 0; i < listViewBrushes.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    brush = adapter.getItem(i).getId();
                }
            }
            if (brush != -1) {
                int finalBrush = brush;
                brushesData.deleteBrush(finalBrush);
                listViewBrushes.clearChoices();
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