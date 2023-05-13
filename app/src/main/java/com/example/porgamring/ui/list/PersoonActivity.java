package com.example.porgamring.ui.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.porgamring.DatePicker;
import com.example.porgamring.R;

public class PersoonActivity extends AppCompatActivity {

    private Button btnUpdate, btnOnder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoon);

        btnOnder = findViewById(R.id.btnOnder);
        btnUpdate = findViewById(R.id.btnUpdate);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PersoonActivity.this, UpdatePersoon.class);
                startActivity(k);
            }
        });

        btnOnder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PersoonActivity.this, OnderdeelActivity.class);
                startActivity(k);
            }
        });
    }
}