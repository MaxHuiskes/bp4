package com.example.porgamring.ui.aansturing;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.BluetoothSend;
import com.example.porgamring.MainActivity;
import com.example.porgamring.databinding.FragmentAansturingBinding;
import com.example.porgamring.model.Persoon;

import java.io.IOException;

public class AansturingFragment extends Fragment {

    private final BluetoothSend bluetoothSend = MainActivity.bluetoothSend;
    private FragmentAansturingBinding binding;
    private Button btnLinks, btnRechts;
    private Persoon bedlicher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AansturingViewModel slideshowViewModel =
                new ViewModelProvider(this).get(AansturingViewModel.class);

        binding = FragmentAansturingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnLinks = binding.btnLinks;
        btnRechts = binding.btnRechts;
        bedlicher = MainActivity.bedlichter;

        String naam = bedlicher.getStrNaam();
        String datum = bedlicher.getDtmDatum().toString();

        btnRechts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (bluetoothSend.isConnected()) {
                        bluetoothSend.send("21");
                        if (!bluetoothSend.getBluetooth().contains("2")) {
                            Toast.makeText(getContext().getApplicationContext(), "Draaien niet gelukt", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    Log.e("IOExeptoin", e.getMessage());
                } catch( NullPointerException e){
                    Log.e("NullPointerException", e.getMessage());
                }
            }
        });

        btnLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (bluetoothSend.isConnected()) {
                        bluetoothSend.send("22");
                        if (!bluetoothSend.getBluetooth().contains("2")) {
                            Toast.makeText(getContext().getApplicationContext(), "Draaien niet gelukt", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    Log.e("IOExeptoin", e.getMessage());
                }
                catch( NullPointerException e){
                    Log.e("NullPointerException", e.getMessage());
                }
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}