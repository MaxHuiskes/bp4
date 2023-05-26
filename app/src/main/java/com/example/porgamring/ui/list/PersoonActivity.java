package com.example.porgamring.ui.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.R;
import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.model.Draai;
import com.example.porgamring.model.Persoon;

import java.util.ArrayList;

public class PersoonActivity extends AppCompatActivity {

    private ArrayList<Draai> lDraai;
    private ArrayList<Persoon> alPersoon;
    private TextView tvNaam, tvDatum, tvBloed, tvGewicht;
    private String datum = "", naam = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoon);

        Button btnOnder = findViewById(R.id.btnOnder);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        ListView listView = findViewById(R.id.lvDraai);
        tvBloed = findViewById(R.id.tvbloed);
        tvDatum = findViewById(R.id.tvDatum);
        tvGewicht = findViewById(R.id.tvGewicht);
        tvNaam = findViewById(R.id.tvNaam);

        APIHandler api = new APIHandler();

        datum = getIntent().getStringExtra("datum");
        naam = getIntent().getStringExtra("naam");
        tvDatum.setText(String.format("%s%s", tvDatum.getText(), datum));
        tvNaam.setText(String.format("%s%s", tvNaam.getText(), naam));

        String finalNaam = naam;
        String finalDatum = datum;
        Thread thPersoonGegevens = new Thread(() -> alPersoon = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/" + finalNaam + "/" + finalDatum));
        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }


        Thread thrdraai = new Thread(() -> lDraai = api.getAlVereiste("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/get/" + finalNaam + "/" + finalDatum));
        try {
            thrdraai.start();
            thrdraai.join();
        } catch (InterruptedException ex) {
            Log.e("InterruptedException", ex.getMessage());
        }

        lDraai.sort((t1, t2) -> t2.getDtmMoment().compareTo(t1.getDtmMoment()));

        String bloed;
        int gewicht;

        bloed = alPersoon.get(0).getStrBloedgroep();
        gewicht = alPersoon.get(0).getIntGewicht();
        tvGewicht.setText(String.format("%s%s", tvGewicht.getText(), gewicht));
        tvBloed.setText(String.format("%s%s", tvBloed.getText(), bloed));


        ArrayAdapter<Draai> arr = new ArrayAdapter<>(PersoonActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lDraai);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        listView.setAdapter(arr);

        try {
            Log.e("listd gewicht", String.valueOf(lDraai.get(0).getPersoon().getIntGewicht()));

        } catch (Exception exc) {
            Log.e("Exception", exc.getMessage());
        }


        btnUpdate.setOnClickListener(view -> {
            Intent k = new Intent(PersoonActivity.this, UpdatePersoon.class);
            k.putExtra("naam", finalNaam);
            k.putExtra("datum", finalDatum);

            k.putExtra("gewicht", alPersoon.get(0).getIntGewicht());
            k.putExtra("bloed", alPersoon.get(0).getStrBloedgroep());

            startActivity(k);
        });

        btnOnder.setOnClickListener(view -> {
            Intent k = new Intent(PersoonActivity.this, OnderdeelActivity.class);
            k.putExtra("naam", finalNaam);
            k.putExtra("datum", finalDatum);
            k.putExtra("work", true);
            startActivity(k);
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        Thread thPersoonGegevens = new Thread(() -> {
            APIHandler api = new APIHandler();
            alPersoon = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/" + naam + "/" + datum);
        });
        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();

            tvBloed.setText("Bloedgoep: " + alPersoon.get(0).getStrBloedgroep());
            tvGewicht.setText("Gewicht: " + alPersoon.get(0).getIntGewicht());
            tvNaam.setText("Naam: " + alPersoon.get(0).getStrNaam());
            tvDatum.setText("Geboorte datum: " + alPersoon.get(0).getDtmDatum());

        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }
    }
}
