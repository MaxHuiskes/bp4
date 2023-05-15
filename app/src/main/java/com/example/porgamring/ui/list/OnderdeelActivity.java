package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.APIHandler;
import com.example.porgamring.R;
import com.example.porgamring.model.Component;
import com.example.porgamring.model.Draai;

import java.util.ArrayList;
import java.util.Collections;

public class OnderdeelActivity extends AppCompatActivity {

    private ListView lvCom;
    private ArrayList<Component> alComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onderdeel);

        lvCom = findViewById(R.id.lvOnder);

        APIHandler api = new APIHandler();

        String naam = "";
        String datum = "";

//        naam = getIntent().getStringExtra("naam");
//        datum = getIntent().getStringExtra("datum");

        String finalDatum = datum;
        String finalNaam = naam;
        Thread thrOnderdeel = new Thread(new Runnable() {
            @Override
            public void run() {
               alComp = api.getAlTrans("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/component/get/" + finalNaam + "/" + finalDatum);
            }
        });

        try{
            thrOnderdeel.start();
            thrOnderdeel.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }

        ArrayAdapter<Component> arr = new ArrayAdapter<Component>(OnderdeelActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                alComp);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvCom.setAdapter(arr);


    }
}