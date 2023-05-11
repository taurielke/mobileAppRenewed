package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coursework.user.Brush;

import java.util.concurrent.atomic.AtomicInteger;

public class DiscountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        int price;
        AtomicInteger result = new AtomicInteger();

        Intent intent = getIntent();
        price = intent.getIntExtra("price", 0);

        Button count = findViewById(R.id.btnDiscount);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewDiscount = findViewById(R.id.textViewDiscount);
        TextView textViewResult = findViewById(R.id.textViewResult);

        textViewPrice.setText(String.valueOf(price));

        count.setOnClickListener(v->{
            if (textViewPrice.getText().equals("")){
                Toast.makeText(this, "Insert a price",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewDiscount.getText().equals("")){
                Toast.makeText(this, "Insert a promocode",
                        Toast.LENGTH_LONG).show();
                return;
            }

            int checkedPrice = Integer.parseInt(textViewPrice.getText().toString());
            String discount = textViewDiscount.getText().toString();

            if (discount.equals("kate")) {
                int gg = checkedPrice * 90 / 100 ;
                result.set(gg);
            }

            if (discount.equals("taurielke")) {
                int gg = checkedPrice * 85 / 100;
                result.set(gg);
            }

            if(!discount.equals("kate") && !discount.equals("taurielke")) {
                result.set(checkedPrice);
                Toast.makeText(this, "No discount with this promocode",
                        Toast.LENGTH_LONG).show();
            }
            textViewResult.setText(String.valueOf(result.get()));

        });
    }
}