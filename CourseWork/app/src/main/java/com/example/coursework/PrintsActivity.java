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

import com.example.coursework.user.Print;
import com.example.coursework.user.PrintsData;

import java.util.Objects;

public class PrintsActivity extends AppCompatActivity {

    String role = "";
    PrintsData printsData;
    ArrayAdapter<Print> adapter;
    ListView listViewPrints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prints);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");

        printsData = new PrintsData(this);

        listViewPrints = findViewById(R.id.listViewBrushes);
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

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,
                printsData.findAllPrints());
        listViewPrints.setAdapter(adapter);
        listViewPrints.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, PrintActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

        disc.setOnClickListener(v -> {
            int print = -1;
            SparseBooleanArray sparseBooleanArray = listViewPrints.getCheckedItemPositions();
            for (int i = 0; i < listViewPrints.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    print = adapter.getItem(i).getId();
                }
            }
            if (print == -1){
                Toast.makeText(this, "Please choose an item",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int price = printsData.getPrint(print).getPrice();
            Intent intent = new Intent(this, DiscountActivity.class);
            intent.putExtra("price", price);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewPrints.clearChoices();
        });

        upd.setOnClickListener(v -> {
            int print = -1;
            SparseBooleanArray sparseBooleanArray = listViewPrints.getCheckedItemPositions();
            for (int i = 0; i < listViewPrints.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    print = adapter.getItem(i).getId();
                }
            }
            if (print == -1){
                return;
            }
            Intent intent = new Intent(this, PrintActivity.class);
            intent.putExtra("Id", print);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewPrints.clearChoices();
        });

        del.setOnClickListener(v -> {
            int print = -1;
            SparseBooleanArray sparseBooleanArray = listViewPrints.getCheckedItemPositions();
            for (int i = 0; i < listViewPrints.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    print = adapter.getItem(i).getId();
                }
            }
            if (print != -1) {
                int finalPrint = print;
                printsData.deletePrint(finalPrint);
                listViewPrints.clearChoices();
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