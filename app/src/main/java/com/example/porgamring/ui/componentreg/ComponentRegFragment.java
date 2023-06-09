package com.example.porgamring.ui.componentreg;

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

import com.example.porgamring.databinding.FragmentComponentRegBinding;
import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.helpers.SentAPI;
import com.example.porgamring.model.Persoon;

import java.util.ArrayList;

public class ComponentRegFragment extends Fragment {

    private FragmentComponentRegBinding binding;

    private EditText etNaamPro, etModel;
    private Spinner spNaamPer;
    private ArrayList<Persoon> arrPersoon = new ArrayList<>();
    private String body;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ComponentRegViewModel galleryViewModel =
                new ViewModelProvider(this).get(ComponentRegViewModel.class);

        binding = FragmentComponentRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etModel = binding.etModel;
        etNaamPro = binding.etNaamPro;
        spNaamPer = binding.spNaamPer;
        Button btnOpslaan = binding.btnOpslaan;

        APIHandler api = new APIHandler();

        Thread thPersoonGegevens = new Thread(() -> arrPersoon = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get"));

        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }

        ArrayAdapter<Persoon> arr = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                arrPersoon);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spNaamPer.setAdapter(arr);


        btnOpslaan.setOnClickListener(view -> {
            String model, product, naam, datum;
            if (etModel.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
            } else {
                model = etModel.getText().toString();
                if (etNaamPro.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                } else {
                    product = etNaamPro.getText().toString();
                    if (spNaamPer.getSelectedItem().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Niet alle gegevens zijn in gevuld", Toast.LENGTH_SHORT).show();
                    } else {
                        Persoon p = (Persoon) spNaamPer.getSelectedItem();
                        naam = p.getStrNaam();
                        datum = p.getDtmDatum();

                        body = "{" +
                                "\"product\":\"" + product + "\"," +
                                "\"naam\":\"" + naam + "\"," +
                                "\"datum\":\"" + datum + "\"," +
                                "\"modelnr\":\"" + model + "\"" +
                                "}";
                        Log.e("body", body);

                        Thread thrCreate = new Thread(() -> {
                            String response = SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/component/post", body);
                        });
                        try {
                            thrCreate.start();
                            thrCreate.join();
                            Toast.makeText(getContext(), "Onderdeel is toegevoegd", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            Log.e("InterruptedException", e.getMessage());
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}