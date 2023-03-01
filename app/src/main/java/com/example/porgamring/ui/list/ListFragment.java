package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.APIHandler;
import com.example.porgamring.databinding.FragmentListBinding;
import com.example.porgamring.model.ProductBarcode;
import com.example.porgamring.model.TransactieVoorraad;
import com.example.porgamring.model.VereisteVoorraad;

import java.util.ArrayList;

public class ListFragment<ListArray> extends Fragment {

    private FragmentListBinding binding;
    private ListView bood;
    private ArrayList<ProductBarcode> barcodeArryaList;
    private ArrayList<TransactieVoorraad> transactieVoorraadArryList;
    private ArrayList<VereisteVoorraad> vereisteVoorraadArraylist;


    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        ListViewModel homeViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Thread thrAllBar = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                barcodeArryaList = api.getAlHups("/producten/get");
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