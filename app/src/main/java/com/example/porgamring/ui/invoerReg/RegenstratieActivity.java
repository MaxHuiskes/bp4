package com.example.porgamring.ui.invoerReg;

import android.icu.text.SimpleDateFormat;
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

import java.util.Date;
import java.util.Locale;

public class RegenstratieActivity extends AppCompatActivity {

    private TextView barcodeText;
    private TextView inhoudText;
    private EditText vereisteText;
    private TextView naamText;
    private Button button;
    private Spinner spEenheid;
    private String bodyver = "{\"barcode\":\"20383695\",\"aantal\":5}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regenstratie);
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
        String aantal = getIntent().getStringExtra("aan");

        button = (Button) findViewById(R.id.schrijf);
        button.setText("Opslaan");//set the text on button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Succes vol opgeslagen", Toast.LENGTH_LONG).show();//display the text of button1
                // opslaan naar
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/producten/insert"
                                , "{\"barcode\": \"" + barcode + "\",\"product\": \"" + naamText.getText() + "\",\"inhoud\": " + inhoudText.getText() + ",\"eenheid\": \"" + spEenheid.getSelectedItem().toString() + "\"}");
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread thver = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/vereiste/insert"
                                , "{\"barcode\": \"" + barcode + "\",\"aantal\":" + vereisteText.getText().toString() + "}");
                        Log.i("thread done", "thread is done");
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
                try {
                    Thread thrAdd = new Thread(new Runnable() {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String timest = currentDate + " " + currentTime;

                        @Override
                        public void run() {
                            Log.i("strat", "start");
                            String body = "{\"barcode\":\"" + barcode
                                    + "\",\"timest\":\"" + timest
                                    + "\",\"aantal\":" + aantal
                                    + ",\"pos\": 1}";
                            Log.i("body", body);
                            SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/aantal/insert"
                                    , body);
                        }
                    });
                    thrAdd.start();
                    thrAdd.join();
                    Toast.makeText(getApplicationContext(), "Product(en) zijn succesful toegevoegd", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.i("thread vereiste", e.getMessage());
                }

                finish();
            }
        });
    }


}