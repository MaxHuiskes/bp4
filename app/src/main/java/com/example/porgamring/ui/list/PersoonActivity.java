package com.example.porgamring.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.APIHandler;
import com.example.porgamring.R;
import com.example.porgamring.model.Draai;
import com.example.porgamring.model.Persoon;

import java.util.ArrayList;

public class PersoonActivity extends AppCompatActivity {

    private Button btnUpdate, btnOnder;
    private ListView listView;
    private ArrayList<Draai> lDraai;
    private ArrayList<Persoon> alPersoon;
    private TextView tvNaam, tvDatum, tvBloed, tvGewicht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoon);

        btnOnder = findViewById(R.id.btnOnder);
        btnUpdate = findViewById(R.id.btnUpdate);
        listView = findViewById(R.id.lvDraai);
        tvBloed = findViewById(R.id.tvbloed);
        tvDatum = findViewById(R.id.tvDatum);
        tvGewicht = findViewById(R.id.tvGewicht);
        tvNaam = findViewById(R.id.tvNaam);

        APIHandler api = new APIHandler();

        boolean work = getIntent().getBooleanExtra("work", false);

        String datum = "";
        String naam = "";

        if (work) {
            datum = getIntent().getStringExtra("datum");
            naam = getIntent().getStringExtra("naam");
            tvDatum.setText(String.format("%s%s", tvDatum.getText(), datum));
            tvNaam.setText(String.format("%s%s", tvNaam.getText(), naam));
        } else {
            datum = "";
            naam = "";
        }


        String finalNaam = naam;
        String finalDatum = datum;
        Thread thPersoonGegevens = new Thread(new Runnable() {
            @Override
            public void run() {
                alPersoon = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/" + finalNaam + "/" + finalDatum);
            }
        });

        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }


        String bloed = "";
        int gewicht = 0;


        if (work) {
            bloed = alPersoon.get(0).getStrBloedgroep();
            gewicht = alPersoon.get(0).getIntGewicht();
            tvGewicht.setText(String.format("%s%s", tvGewicht.getText(), gewicht));
            tvBloed.setText(String.format("%s%s", tvBloed.getText(), bloed));
        } else {
            bloed = "";
            gewicht = 0;
        }



        Thread thrdraai = new Thread(new Runnable() {
            @Override
            public void run() {
                lDraai = api.getAlVereiste("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/get/" + finalNaam + "/" + finalDatum);
            }
        });
        try {
            thrdraai.start();
            thrdraai.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }

        ArrayAdapter<Draai> arr = new ArrayAdapter<Draai>(PersoonActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lDraai);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (work)
        listView.setAdapter(arr);

        try {
            Log.e("listd gewicht", String.valueOf(lDraai.get(0).getPersoon().getIntGewicht()));

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PersoonActivity.this, UpdatePersoon.class);
                k.putExtra("naam", finalNaam);
                k.putExtra("datum", finalDatum);
                startActivity(k);
            }
        });

        btnOnder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PersoonActivity.this, OnderdeelActivity.class);
                k.putExtra("naam", finalNaam);
                k.putExtra("datum", finalDatum);
                startActivity(k);
            }
        });
    }
}