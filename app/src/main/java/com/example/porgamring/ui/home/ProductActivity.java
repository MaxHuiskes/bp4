package com.example.porgamring.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.porgamring.APIHandler;
import com.example.porgamring.R;
import com.example.porgamring.model.ProductBarcode;
import com.example.porgamring.model.TransactieVoorraad;
import com.example.porgamring.model.VereisteVoorraad;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private TextView barecode, naam, inhoudEenheid, vereisteAa, current;
    private ArrayList<ProductBarcode> dataArrayList;
    private ArrayList<VereisteVoorraad> vereisteVoorraadArrayList;
    private ArrayList<TransactieVoorraad> barcodeListver;
    private int currentAantal;
    private ListView listTrans;
    private Button wijzig;

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

            int inhoud = dataArrayList.get(0).getIntInhoud();
            String barcode = dataArrayList.get(0).getStrBarcode();
            String name = dataArrayList.get(0).getStrProduct();
            String eenheid = dataArrayList.get(0).getStrEenheid();

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



    }
}