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

import com.example.porgamring.MainActivity;
import com.example.porgamring.databinding.FragmentBluethootBinding;
import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.helpers.BluetoothSend;
import com.example.porgamring.helpers.MySpinner;
import com.example.porgamring.model.Persoon;

import java.io.IOException;
import java.util.ArrayList;

public class BluethootFragment extends Fragment {

    private final BluetoothSend bluetoothSend = MainActivity.bluetoothSend;
    private ListView list;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private TextView tvStatus;
    private ArrayList<Persoon> persoonArrayList;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BluethootViewModel slideshowViewModel =
                new ViewModelProvider(this).get(BluethootViewModel.class);

        com.example.porgamring.databinding.FragmentBluethootBinding binding = FragmentBluethootBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner spPer = binding.spPers;

        APIHandler api = new APIHandler();


        Button btnDis = binding.btnDis;
        Button btnlijst = binding.btnList;
        list = binding.lvList;
        tvStatus = binding.tvStatus;
        pairedDevicesArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        tvStatus.setText("Geen verbinding.");

        Thread thPersoonGegevens = new Thread(() -> persoonArrayList = api.getAlHups("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get"));

        try {
            thPersoonGegevens.start();
            thPersoonGegevens.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        }

        ArrayAdapter<Persoon> arr = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                persoonArrayList);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPer.setAdapter(arr);

        MySpinner.selectSpinnerValue(spPer, "max 2000-10-28");

        spPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.bedlichter = persoonArrayList.get(i);
                Toast.makeText(getActivity(),
                                MainActivity.bedlichter.toString(),
                                Toast.LENGTH_LONG)
                        .show();
                Log.e("\t\t\t\t\t\t\t\t\t", persoonArrayList.get(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnlijst.setOnClickListener(view -> bluetoothSend.showPairedDevices(list, pairedDevicesArrayAdapter));

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

        list.setOnItemClickListener((adapterView, view, i, l) -> {
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
        });

        return root;
    }



}