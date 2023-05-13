package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porgamring.DatePicker;
import com.example.porgamring.R;

public class UpdatePersoon extends AppCompatActivity {

    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_persoon);

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
    }
}