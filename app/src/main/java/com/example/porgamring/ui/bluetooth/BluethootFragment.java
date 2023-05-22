package com.example.porgamring.ui.bluetooth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.helpers.BluetoothSend;
import com.example.porgamring.MainActivity;
import com.example.porgamring.databinding.FragmentBluethootBinding;
import com.example.porgamring.model.Persoon;

import java.io.IOException;
import java.util.ArrayList;

public class BluethootFragment extends Fragment {

    private final BluetoothSend bluetoothSend = MainActivity.bluetoothSend;
    private FragmentBluethootBinding binding;
    private Button btnDis, btnlijst;
    private ListView list;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private TextView tvStatus;
    private ArrayList<Persoon> persoonArrayList;
    private Spinner spPer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BluethootViewModel slideshowViewModel =
                new ViewModelProvider(this).get(BluethootViewModel.class);

        binding = FragmentBluethootBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        spPer = binding.spPers;

        APIHandler api = new APIHandler();



        btnDis = binding.btnDis;
        btnlijst = binding.btnList;
        list = binding.lvList;
        tvStatus = binding.tvStatus;
        pairedDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        tvStatus.setText("Geen verbinding.");

        ArrayList<Persoon> arrPersoon = new ArrayList<Persoon>();
        ArrayAdapter<Persoon> arr = new ArrayAdapter<Persoon>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                arrPersoon);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Thread thPersoonGegevens = new Thread(new Runnable() {
            @Override
            public void run() {
                persoonArrayList = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get");
            }
        });

        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }

        arrPersoon.addAll(persoonArrayList);

        spPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.bedlichter = arrPersoon.get(i);
                Toast.makeText(getActivity(),
                                MainActivity.bedlichter.toString(),
                                Toast.LENGTH_LONG)
                        .show();
                Log.e("\t\t\t\t\t\t\t\t\t", arrPersoon.get(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spPer.setAdapter(arr);

        btnlijst.setOnClickListener(view -> {
            bluetoothSend.showPairedDevices(list, pairedDevicesArrayAdapter);
        });

        btnDis.setOnClickListener(view -> {
            try {
                if (bluetoothSend.isConnected()) {
                    bluetoothSend.closeConnection();
                    if (!bluetoothSend.isConnected()) {
                        tvStatus.setText("Geen verbinding.");
                        Toast.makeText(getContext().getApplicationContext(), "Verbinding is verbroken.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IOException e) {
                Log.e("IOException e", e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NullPointerException e", e.getMessage());
            }

        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String send;
                String device = list.getAdapter().getItem(i).toString();
                Log.i("diveice name", device);

                try {
                    bluetoothSend.createConnection(device);
                } catch (IOException e) {
                    Log.e("IOExeptoin", e.getMessage());
                } finally {
                    try {
                        if (bluetoothSend.isConnected()) {
                            tvStatus.setText("Verbonden.");
                            pairedDevicesArrayAdapter.clear();
                            Toast.makeText(getContext().getApplicationContext(), "Verbinding is gemaakt.", Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        Log.e("IOException e", e.getMessage());
                    }
                }
            }
        });

        return root;
    }

}