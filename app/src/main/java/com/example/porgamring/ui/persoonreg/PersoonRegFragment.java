package com.example.porgamring.ui.persoonreg;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.DatePicker;
import com.example.porgamring.SentAPI;
import com.example.porgamring.databinding.FragmentPersoonRegBinding;

import java.util.ArrayList;


public class PersoonRegFragment<ListArray> extends Fragment {

    private FragmentPersoonRegBinding binding;
    private FragmentPersoonRegBinding FragmentProductListBinding;
    private Button dateButton, btnOpslaan;
    private EditText etNaam, etGewicht;
    private Spinner sBloed;
    private String body;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersoonRegViewModel homeViewModel =
                new ViewModelProvider(this).get(PersoonRegViewModel.class);

        binding = FragmentPersoonRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateButton = binding.datePickerButton;
        DatePicker datePicker = new DatePicker(dateButton, getActivity());
        datePicker.initDatePicker(false);

        dateButton.setText(datePicker.getTodaysDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.datePickerDialog.show();
            }
        });

        etGewicht = binding.etGewicht;
        etNaam = binding.etNaam;

        sBloed = binding.sBloed;
        btnOpslaan = binding.btnOpslaan;
        ArrayList<String> arbloed = new ArrayList<>();
        arbloed.add("A-");
        arbloed.add("A+");
        arbloed.add("B-");
        arbloed.add("B+");
        arbloed.add("AB-");
        arbloed.add("AB-");
        arbloed.add("0-");
        arbloed.add("0+");

        ArrayAdapter<String> arr = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arbloed);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBloed.setAdapter(arr);


        btnOpslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gewicht, bloed, naam;
                String datum;
                if (etGewicht.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                } else {
                    gewicht = etGewicht.getText().toString();
                    if (etNaam.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                    } else {
                        naam = etNaam.getText().toString();
                        if (sBloed.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                        } else {
                            bloed = sBloed.getSelectedItem().toString();
                            if (dateButton.getText().toString().equals(datePicker.getTodaysDate())) {
                                Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                            } else {
                                datum = dateButton.getText().toString();
                                body = "{" +
                                        "\"naam\":\"" + naam + "\"," +
                                        "\"datum\":\"" + datum + "\"," +
                                        "\"gewicht\":" + gewicht + "," +
                                        "\"bloed\":\"" + bloed + "\"" +
                                        "}";
                                Log.e("body", body);

                                Thread thrCreate = new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String response = SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/post",body);

                                    }
                                });
                                try {
                                    thrCreate.start();
                                    thrCreate.join();
                                    Toast.makeText(getContext(), "Persoon is geregistreerd", Toast.LENGTH_SHORT).show();
                                } catch (InterruptedException e) {
                                    Log.e("InterruptedException", e.getMessage());

                                }
                            }
                        }
                    }
                }
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
