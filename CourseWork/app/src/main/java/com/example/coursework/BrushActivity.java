package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coursework.user.Brush;
import com.example.coursework.user.BrushesData;

public class BrushActivity extends AppCompatActivity {
    
    int id = -1;
    BrushesData brushesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        brushesData = new BrushesData(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.btnSave);
        TextView textViewName = findViewById(R.id.textViewNamePrint);
        TextView textViewDescription = findViewById(R.id.textViewSizePrint);
        TextView textViewPrice = findViewById(R.id.textViewPricePrint);

        if (id != -1){
            Brush brush = brushesData.getBrush(id);
            if (brush != null){
                textViewName.setText(brush.getName());
                textViewDescription.setText(brush.getDescription());
                textViewPrice.setText(String.valueOf(brush.getPrice()));
            }
        }

        save.setOnClickListener(v -> {
            if (textViewName.getText().equals("")){
                Toast.makeText(this, "Insert a name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewDescription.getText().equals("")){
                Toast.makeText(this, "Insert a description",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewPrice.getText().equals("")){
                Toast.makeText(this, "Insert a price",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String name = textViewName.getText().toString();
            String description = textViewDescription.getText().toString();
            int price = Integer.parseInt(textViewPrice.getText().toString());
            if (id != -1){
                brushesData.updateBrush(id, name, description, price);
            }
            else {
                brushesData.addBrush(name, description, price);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}