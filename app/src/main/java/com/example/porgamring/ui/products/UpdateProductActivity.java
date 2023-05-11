package com.example.porgamring.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.R;
import com.example.porgamring.SentAPI;

public class UpdateProductActivity extends AppCompatActivity {
    private TextView barcodeText;
    private TextView inhoudText;
    private EditText vereisteText;
    private TextView naamText;
    private Button button;
    private Spinner spEenheid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        barcodeText = findViewById(R.id.barCode);
        inhoudText = findViewById(R.id.inhoudet);
        naamText = findViewById(R.id.porductet);
        vereisteText = findViewById(R.id.vereisteet);
        spEenheid = findViewById(R.id.spEenheid);
        String[] arraySpinner = new String[]{
                "kg", "g", "mL", "L"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEenheid.setAdapter(adapter);

        String barcode = getIntent().getStringExtra("barcode");
        barcodeText.setText(barcode);
        String ver = getIntent().getStringExtra("ver");
        String naam = getIntent().getStringExtra("naam");
        String inhoud = getIntent().getStringExtra("inhoud");
        String eenheid = getIntent().getStringExtra("eenheid");

        button = (Button) findViewById(R.id.schrijf);
        button.setText("Opslaan");//set the text on button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Succes vol opgeslagen", Toast.LENGTH_SHORT).show();//display the text of button1
                // opslaan naar
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String naamTex;
                        if (naamText.getText().toString().isEmpty()) {
                            naamTex = naam;
                        } else {
                            naamTex = naamText.getText().toString();
                        }
                        String inhoudT;
                        if (inhoudText.getText().toString().isEmpty()) {
                            inhoudT = inhoud;
                        } else {
                            inhoudT = inhoudText.getText().toString();
                        }
                        String eenheidT;
                        if (spEenheid.getSelectedItem().toString().isEmpty()) {
                            eenheidT = naam;
                        } else {
                            eenheidT = spEenheid.getSelectedItem().toString();
                        }
                        String body = "{\"barcode\": \"" + barcode + "\",\"product\": \"" + naamTex + "\",\"inhoud\": " + inhoudT + ",\"eenheid\": \"" + eenheidT + "\"}";
                        Log.i("product\t\t\t", naamTex + "\t" + inhoudT + "\t" + eenheidT);
                        Log.i("body", body);
                        SentAPI.put("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/producten/update"
                                , body);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("thread update", e.getMessage());
                }
                Thread thver = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String aan = vereisteText.getText().toString();
                        if (ver.equals("Vereiste aantal: geen vereiste aantal")) {
                            SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/vereiste/insert"
                                    , "{\"barcode\": \"" + barcode + "\",\"aantal\":" + aan + "}");
                        } else {
                            Log.i("vereiste", aan);
                            SentAPI.put("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/vereiste/update"
                                    , "{\"barcode\": \"" + barcode + "\",\"aantal\":" + aan + "}");
                        }
                    }
                });
                String vereistet = vereisteText.getText().toString();
                if (!vereistet.isEmpty()) {
                    thver.start();
                    try {
                        thver.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        });

    }
}