package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.DatePicker;
import com.example.porgamring.R;
import com.example.porgamring.SentAPI;

import java.util.ArrayList;

public class UpdatePersoon extends AppCompatActivity {

    private Button dateButton, btnOpslaan;
    private EditText etGewicht, etNaam;
    private Spinner sBloed;
    private String naamOud = "", datumOud = "", bloedgroepOud = "", naam = "", datum = "", bloedgroep = "";
    private int gewichtOud = 0, gewicht = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_persoon);

        etGewicht = findViewById(R.id.etGewicht);
        etNaam = findViewById(R.id.etNaam);
        sBloed = findViewById(R.id.sBloed);
        btnOpslaan = findViewById(R.id.btnOpslaan);

        ArrayList<String> arbloed = new ArrayList<>();
        arbloed.add("niet veranderen");
        arbloed.add("A-");
        arbloed.add("A+");
        arbloed.add("B-");
        arbloed.add("B+");
        arbloed.add("AB-");
        arbloed.add("AB-");
        arbloed.add("0-");
        arbloed.add("0+");

        ArrayAdapter<String> arr = new ArrayAdapter<String>(UpdatePersoon.this,
                android.R.layout.simple_spinner_dropdown_item,
                arbloed);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBloed.setAdapter(arr);

        String naamOud = getIntent().getStringExtra("naam");
        String datumOud = getIntent().getStringExtra("datum");
        int gewichtOud = getIntent().getIntExtra("gewicht", 0);
        String bloedgroepOud = getIntent().getStringExtra("bloed");

        dateButton = findViewById(R.id.datePickerButton);
        DatePicker datePicker = new DatePicker(dateButton, this);
        datePicker.initDatePicker(false);

        dateButton.setText(datePicker.getTodaysDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.datePickerDialog.show();
            }
        });

        String finalBloedgroepOud = bloedgroepOud;
        String finalNaamOud = naamOud;
        int finalGewichtOud = gewichtOud;
        String finalDatumOud = datumOud;


        btnOpslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thrUpdate = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (sBloed.getSelectedItem().toString().equals("niet veranderen")) {
                            bloedgroep = finalBloedgroepOud;
                        } else {
                            bloedgroep = sBloed.getSelectedItem().toString();
                        }
                        if (etNaam.getText().toString().isEmpty()) {
                            naam = finalNaamOud;
                        } else {
                            naam = etNaam.getText().toString();
                        }
                        if (etGewicht.getText().toString().isEmpty()) {
                            gewicht = finalGewichtOud;
                        } else {
                            gewicht = Integer.getInteger(etGewicht.getText().toString());
                        }
                        if (dateButton.getText().toString().equals(datePicker.getTodaysDate())) {
                            datum = finalDatumOud;
                        } else {
                            datum = dateButton.getText().toString();
                        }
                        String body = "{" +
                                "\"naam\":\"" + naam + "\"," +
                                "\"datum\":\"" + datum + "\"," +
                                "\"gewicht\":" + gewicht + "," +
                                "\"bloed\":\"" + bloedgroep + "\"," +
                                "\"onaam\":\"" + finalNaamOud + "\"," +
                                "\"odate\":\"" + finalDatumOud + "\"" +
                                "}";
                        Log.e("body", body);
                        SentAPI.put("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/put", body);
                    }
                });
                try {
                    thrUpdate.start();
                    thrUpdate.join();
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
                }
                finish();
            }
        });
    }
}