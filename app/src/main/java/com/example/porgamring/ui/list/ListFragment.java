package com.example.porgamring.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.databinding.FragmentPersonenBinding;
import com.example.porgamring.model.Persoon;

import java.util.ArrayList;

public class ListFragment<ListArray> extends Fragment {

    private FragmentPersonenBinding binding;
    private Button btn;
    private ListView listView;
    private ArrayList<Persoon> alPersoon = new ArrayList<Persoon>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel homeViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentPersonenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn = binding.btn;
        listView = binding.personen;

        APIHandler api = new APIHandler();

        Thread thPersoonGegevens = new Thread(new Runnable() {
            @Override
            public void run() {
                alPersoon = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get");
            }
        });

        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }



        ArrayAdapter<Persoon> arr = new ArrayAdapter<Persoon>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                alPersoon);

        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listView.setAdapter(arr);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String entry = listView.getAdapter().getItem(i).toString();
                Log.i("parent", entry);

                String naam = alPersoon.get(i).getStrNaam();
                String datum = alPersoon.get(i).getDtmDatum();
                String bloed = alPersoon.get(i).getStrBloedgroep();
                int gewicht = alPersoon.get(i).getIntGewicht();

                Intent k = new Intent(getActivity(), PersoonActivity.class);
                k.putExtra("naam", naam);
                k.putExtra("datum", datum);
                k.putExtra("bloed", bloed);
                k.putExtra("gewicht", gewicht);
                k.putExtra("work",true);
                try {
                    startActivity(k);
                } catch (Exception ex) {
                    Log.i("activity", ex.getMessage());
                }
                Toast.makeText(getActivity(), "open item", Toast.LENGTH_SHORT).show();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}