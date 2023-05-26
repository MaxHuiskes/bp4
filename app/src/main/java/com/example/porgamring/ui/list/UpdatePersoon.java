package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.R;
import com.example.porgamring.helpers.DatePicker;
import com.example.porgamring.helpers.MySpinner;
import com.example.porgamring.helpers.SentAPI;

import java.util.ArrayList;

public class UpdatePersoon extends AppCompatActivity {

    private Button dateButton;
    private EditText etGewicht, etNaam;
    private Spinner sBloed;
    private String naam = "", datum = "", bloedgroep = "";
    private int gewicht = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_persoon);

        etGewicht = findViewById(R.id.etGewicht);
        etNaam = findViewById(R.id.etNaam);
        sBloed = findViewById(R.id.sBloed);
        Button btnOpslaan = findViewById(R.id.btnOpslaan);

        ArrayList<String> arbloed = new ArrayList<>();
        arbloed.add("A-");
        arbloed.add("A+");
        arbloed.add("B-");
        arbloed.add("B+");
        arbloed.add("AB-");
        arbloed.add("AB-");
        arbloed.add("0-");
        arbloed.add("0+");

        ArrayAdapter<String> arr = new ArrayAdapter<>(UpdatePersoon.this,
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

        dateButton.setOnClickListener(view -> datePicker.datePickerDialog.show());

        MySpinner.selectSpinnerValue(sBloed, bloedgroepOud);

        btnOpslaan.setOnClickListener(view -> {
            Thread thrUpdate = new Thread(() -> {
                bloedgroep = sBloed.getSelectedItem().toString();
                if (etNaam.getText().toString().isEmpty()) {
                    naam = naamOud;
                } else {
                    naam = etNaam.getText().toString();
                }
                if (etGewicht.getText().toString().isEmpty()) {
                    gewicht = gewichtOud;
                } else {
                    //noinspection ConstantConditions
                    gewicht = Integer.getInteger(etGewicht.getText().toString());
                }
                if (dateButton.getText().toString().equals(datePicker.getTodaysDate())) {
                    datum = datumOud;
                } else {
                    datum = dateButton.getText().toString();
                }
                String body = "{" +
                        "\"naam\":\"" + naam + "\"," +
                        "\"datum\":\"" + datum + "\"," +
                        "\"gewicht\":" + gewicht + "," +
                        "\"bloed\":\"" + bloedgroep + "\"," +
                        "\"onaam\":\"" + naamOud + "\"," +
                        "\"odate\":\"" + datumOud + "\"" +
                        "}";
                Log.e("body", body);
                if (datum.equals(datumOud)) {
                    SentAPI.put("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/putOld", body);
                } else {
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
        });
    }
}