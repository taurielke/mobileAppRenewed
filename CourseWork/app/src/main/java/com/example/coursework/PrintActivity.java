package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coursework.user.Print;
import com.example.coursework.user.PrintsData;

public class PrintActivity extends AppCompatActivity {

    int id = -1;
    PrintsData printsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        printsData = new PrintsData(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.btnSave);
        TextView tvName = findViewById(R.id.textViewNamePrint);
        TextView tvSize = findViewById(R.id.textViewSizePrint);
        TextView tvPrice = findViewById(R.id.textViewPricePrint);

        if (id != -1){
            Print print = printsData.getPrint(id);
            if (print != null){
                tvName.setText(print.getName());
                tvSize.setText(print.getSize());
                tvPrice.setText(String.valueOf(print.getPrice()));
            }
        }

        save.setOnClickListener(v -> {
            if (tvName.getText().equals("")){
                Toast.makeText(this, "Insert a name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (tvSize.getText().equals("")){
                Toast.makeText(this, "Insert a description",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (tvPrice.getText().equals("")){
                Toast.makeText(this, "Insert a price",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String name = tvName.getText().toString();
            String size = tvSize.getText().toString();
            int price = Integer.parseInt(tvPrice.getText().toString());
            if (id != -1){
                printsData.updatePrint(id, name, size, price);
            }
            else {
                printsData.addPrint(name, size, price);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}