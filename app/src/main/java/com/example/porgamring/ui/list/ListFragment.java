package com.example.porgamring.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class ListFragment<ListArray> extends Fragment {

    private FragmentListBinding binding;
    private ListView bood;
    private ArrayList<ProductBarcode> barcodeArryaList;
    private ArrayList<TransactieVoorraad> transactieVoorraadArryList;
    private ArrayList<VereisteVoorraad> vereisteVoorraadArraylist;
    private int aantalAan;
    private int aantalVereist;
    private List<String> boodlijst = new ArrayList<String>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel homeViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bood = binding.bood;
        ArrayAdapter<String> arr = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                boodlijst);

        bood.setAdapter(arr);

        Thread thrAllBar = new Thread(new Runnable() {
            @Override
            public void run() {
                APIHandler api = new APIHandler();
                barcodeArryaList = api.getAlHups("/producten/get");
            }
        });

        try {
            thrAllBar.start();
            thrAllBar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.i("EXception", e.getMessage());
        }

        for (ProductBarcode o : barcodeArryaList) {
            String barcode = o.getStrBarcode();
            Thread vereiste = new Thread(new Runnable() {
                @Override
                public void run() {
                    APIHandler api = new APIHandler();
                    vereisteVoorraadArraylist = api.getAlVereiste("vereiste/get/" + barcode);
                    if (!vereisteVoorraadArraylist.isEmpty()) {
                        aantalVereist = vereisteVoorraadArraylist.get(0).getAantal();
                    } else {
                        aantalVereist = 0;
                    }
                    Log.i(barcode+"ver",String.valueOf( aantalVereist));
                }
            });

            vereiste.start();
            Thread thrver = new Thread(new Runnable() {
                @Override
                public void run() {
                    APIHandler api = new APIHandler();
                    transactieVoorraadArryList = api.getAlTrans("aantal/get/" + barcode);

                    for (TransactieVoorraad o : transactieVoorraadArryList) {
                        if (o.isBoolPos() == 1) {
                            aantalAan = aantalAan + o.getAantal();
                        } else if (o.isBoolPos() == 0) {
                            aantalAan = aantalAan - o.getAantal();
                        }
                        else{
                            aantalAan = aantalAan + 0;
                        }
                    }
                    Log.i(barcode+"aan",String.valueOf( aantalAan));
                }
            });

            try {
                thrver.start();
                thrver.join();
                vereiste.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.i("Exeption", e.getMessage());
            }

            Thread addList = new Thread(new Runnable() {
                @Override
                public void run() {
                    int wel = aantalVereist - aantalAan;
                    Log.i("\t\t\t\t", String.valueOf(wel));
                    if (wel >= 1) {
                        String boodschap =  wel+ " keer "+o.toString();
                        boodlijst.add(boodschap);
                        Log.i("Bood\t\t\t\t", boodlijst.toString());
                        aantalAan = 0;
                        aantalVereist = 0;
                    }
                }
            });

            try{
                addList.start();
                addList.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.i("interrupt\t\t\t", e.getMessage());
            }


            Log.i("Bood\t\t\t\t", boodlijst.toString());

        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}