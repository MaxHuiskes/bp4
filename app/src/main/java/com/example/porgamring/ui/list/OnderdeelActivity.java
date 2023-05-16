package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.APIHandler;
import com.example.porgamring.R;
import com.example.porgamring.SentAPI;
import com.example.porgamring.model.Component;

import java.util.ArrayList;

public class OnderdeelActivity extends AppCompatActivity {

    private ListView lvCom;
    private ArrayList<Component> alComp;
    private Component component;
    private Button btnVerwijder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onderdeel);

        lvCom = findViewById(R.id.lvOnder);
        btnVerwijder = findViewById(R.id.btnVerwijder);

        APIHandler api = new APIHandler();

        String naam = "";
        String datum = "";

        naam = getIntent().getStringExtra("naam");
        datum = getIntent().getStringExtra("datum");

        String finalDatum = datum;
        String finalNaam = naam;
        Thread thrOnderdeel = new Thread(new Runnable() {
            @Override
            public void run() {
                alComp = api.getAlTrans("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/component/get/" + finalNaam + "/" + finalDatum);
            }
        });

        try {
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


        lvCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                component = (Component) lvCom.getAdapter().getItem(i);
                Log.e("component", component.getStrModelNummer());
                Log.e("component", component.getPersoon().toString());
                Log.e("component", component.getStrProduct());
            }
        });


        btnVerwijder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("component", component.getStrModelNummer());
                Log.e("component", component.getPersoon().toString());
                Log.e("component", component.getStrProduct());

                Thread thrDel = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("delete", "start");
                            SentAPI.delete("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/component/delete/"
                                    + component.getStrProduct() + "/"
                                    + component.getPersoon().getStrNaam() + "/"
                                    + component.getPersoon().getDtmDatum() + "/"
                                    + component.getStrModelNummer());
                            Log.e("delete", "END");
                        } catch (Exception e) {
                            Log.e("IOException", e.getMessage());
                        }
                    }
                });
                try {
                    thrDel.start();
                    thrDel.join();
                    alComp.remove(component);
                    arr.notifyDataSetChanged();
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
                }
            }
        });
    }
}