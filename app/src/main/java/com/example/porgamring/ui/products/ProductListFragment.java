package com.example.porgamring.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.APIHandler;
import com.example.porgamring.databinding.FragmentProductListBinding;
import com.example.porgamring.model.ProductBarcode;

import java.util.ArrayList;

public class ProductListFragment<ListArray> extends Fragment {

    private FragmentProductListBinding binding;
    private ListView list;
    private ArrayList<ProductBarcode> dataArrayList;
    private FragmentProductListBinding FragmentProductListBinding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductListViewModel homeViewModel =
                new ViewModelProvider(this).get(ProductListViewModel.class);

        binding = FragmentProductListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                Log.i("enter","enter getAlHups()");
                dataArrayList = api.getAlHups("producten/get");
                Log.i("leaving","leave getAlHups()");
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.i("", e.toString());
        }

        list = binding.listView;
        ArrayAdapter<ProductBarcode> arr = new ArrayAdapter<ProductBarcode>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                dataArrayList);

        list.setAdapter(arr);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String entry= list.getAdapter().getItem(i).toString();
                Log.i("parent", entry);

                String barcode = entry.substring(0,entry.indexOf(' '));
                Log.i("barcode", barcode);

                Intent k = new Intent(getActivity(), ProductActivity.class);
                k.putExtra("entry", barcode);
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
