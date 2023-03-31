package com.example.porgamring.ui.products;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.porgamring.APIHandler;
import com.example.porgamring.R;
import com.example.porgamring.SentAPI;
import com.example.porgamring.model.ProductBarcode;
import com.example.porgamring.model.TransactieVoorraad;
import com.example.porgamring.model.VereisteVoorraad;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {
    private TextView barecode, naam, inhoudEenheid, vereisteAa, current;
    private ArrayList<ProductBarcode> dataArrayList;
    private ArrayList<VereisteVoorraad> vereisteVoorraadArrayList;
    private ArrayList<TransactieVoorraad> barcodeListver;
    private int currentAantal;
    private ListView listTrans;
    private Button wijzig;
    private int inhoud;
    private String eenheid;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        barecode = findViewById(R.id.barcode1);
        naam = findViewById(R.id.naam);
        inhoudEenheid = findViewById(R.id.inhoudeenheid);
        vereisteAa = findViewById(R.id.vereistAantal);
        current = findViewById(R.id.currentaantal);
        listTrans =findViewById(R.id.transview);
        wijzig = findViewById(R.id.wijzig);

        String entry = getIntent().getStringExtra("entry");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                Log.i("enter","enter getAlHups()");
                Log.i("enter","producten/get_barcode/"+entry);
                dataArrayList = api.getAlHups("producten/get_barcode/"+entry);
                Log.i("leaving","leave getAlHups()");
            }
        });

        thread.start();

        Thread vereiste = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                Log.i("enter","enter getAlHups()");
                Log.i("enter","vereiste/get/"+entry);
                vereisteVoorraadArrayList = api.getAlVereiste("vereiste/get/"+entry);
                Log.i("leaving","leave getAlHups()");
            }
        });

        vereiste.start();

        Thread thrver = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                Log.i("enter", "enter getAlHups()");
                Log.i("enter", "aantal/get/" + entry);
                barcodeListver = api.getAlTrans("aantal/get/" + entry);
                Log.i("leaving", "leave getAlver");
                Log.i("barcodeListver", barcodeListver.toString());
                for (TransactieVoorraad o : barcodeListver) {
                    Log.i("bool\t\t\t\t\t\t", String.valueOf(o.isBoolPos()));
                    if (o.isBoolPos() == 1) {
                        currentAantal = currentAantal + o.getAantal();
                        Log.i("\t\t\t\t", String.valueOf(currentAantal));
                    } else if (o.isBoolPos() == 0) {
                        currentAantal = currentAantal - o.getAantal();
                        Log.i("\t\t\t\t", String.valueOf(currentAantal));
                    }
                }
            }
        });

        try {
            thrver.start();
            thrver.join();
            current.setText(String.format("Aantal in voorraad: %d", currentAantal));


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<TransactieVoorraad> arr = new ArrayAdapter<TransactieVoorraad>(this,
                android.R.layout.simple_spinner_dropdown_item,
                barcodeListver);
        listTrans.setAdapter(arr);

        try {
            thread.join();
            vereiste.join();

            inhoud = dataArrayList.get(0).getIntInhoud();
            String barcode = dataArrayList.get(0).getStrBarcode();
            String name = dataArrayList.get(0).getStrProduct();
            eenheid = dataArrayList.get(0).getStrEenheid();

            barecode.setText(String.format("Barcode: %s", barcode));
            naam.setText(String.format("Product: %s", name));
            inhoudEenheid.setText(String.format("Inhoud: %d %s", inhoud, eenheid));

        } catch (InterruptedException e) {
            Log.i("InterruptedExeption", e.toString());
        }catch (Exception e){
            Log.i("Exception", e.toString());
        }
        int aantal;
        try {
            aantal = vereisteVoorraadArrayList.get(0).getAantal();
            vereisteAa.setText(String.format("Vereiste aantal: %d", aantal));
        }catch (Exception e){
            vereisteAa.setText("Vereiste aantal: geen vereiste aantal");
        }
        wijzig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(ProductActivity.this, UpdateProductActivity.class);
                k.putExtra("barcode", entry);
                k.putExtra("ver", vereisteAa.getText());
                k.putExtra("inhoud", String.valueOf(inhoud));
                k.putExtra("eenheid", eenheid);
                k.putExtra("naam", naam.getText());
                try {
                    startActivity(k);
                } catch (Exception ex) {
                    Log.i("activity", ex.getMessage());
                }
            }
        });


    }
}