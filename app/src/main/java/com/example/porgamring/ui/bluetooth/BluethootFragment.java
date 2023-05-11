package com.example.porgamring.ui.bluetooth;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.porgamring.BluetoothSend;
import com.example.porgamring.MainActivity;
import com.example.porgamring.R;
import com.example.porgamring.databinding.FragmentAansturingBinding;
import com.example.porgamring.databinding.FragmentBluethootBinding;
import com.example.porgamring.ui.aansturing.AansturingViewModel;

import java.io.IOException;

public class BluethootFragment extends Fragment {

    private FragmentBluethootBinding binding;
    private Button btnDis, btnlijst;
    private ListView list;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private TextView tvStatus;
    private final BluetoothSend bluetoothSend = MainActivity.bluetoothSend;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BluethootViewModel slideshowViewModel =
                new ViewModelProvider(this).get(BluethootViewModel.class);

        binding = FragmentBluethootBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnDis = binding.btnDis;
        btnlijst = binding.btnList;
        list = binding.lvList;
        tvStatus = binding.tvStatus;
        pairedDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

        btnlijst.setOnClickListener(view -> {
            bluetoothSend.showPairedDevices(list, pairedDevicesArrayAdapter);
        });

        btnDis.setOnClickListener(view -> {
            try {
                if (bluetoothSend.isConnected()) {
                    bluetoothSend.closeConnection();
                    if (!bluetoothSend.isConnected()) {
                        tvStatus.setText("Geen verbinding.");
                        Toast.makeText(getContext().getApplicationContext(),"Verbinding is verbroken.",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext().getApplicationContext(),"Verbinding is gemaakt.",Toast.LENGTH_SHORT).show();

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