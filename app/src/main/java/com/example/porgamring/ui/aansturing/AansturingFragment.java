package com.example.porgamring.ui.aansturing;

import android.icu.text.SimpleDateFormat;
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
import com.example.porgamring.SentAPI;
import com.example.porgamring.databinding.FragmentAansturingBinding;
import com.example.porgamring.model.Persoon;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

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

        btnRechts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thrRechts = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String timest = currentDate + " " + currentTime;

                        String body = "{" +
                                "\"timest\":\"" + timest + "\"," +
                                "\"naam\":\"" + bedlicher.getStrNaam() + "\"," +
                                "\"datum\":\"" + bedlicher.getDtmDatum() + "\"," +
                                "\"kant\":\"R\"" +
                                "}";
                        SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/post", body);
                    }
                });
                try {
                    if (bluetoothSend.isConnected()) {
                        String naam = bedlicher.getStrNaam();
                        String datum = bedlicher.getDtmDatum().toString();
                        bluetoothSend.send("21");
                        if (!bluetoothSend.getBluetooth().contains("2")) {
                            Toast.makeText(getContext().getApplicationContext(), "Draaien niet gelukt", Toast.LENGTH_SHORT).show();
                            thrRechts.start();
                            thrRechts.join();
                        }
                    }
                } catch (IOException e) {
                    Log.e("IOExeptoin", e.getMessage());
                } catch (NullPointerException e) {
                    Log.e("NullPointerException", e.getMessage());
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
                }
            }
        });

        btnLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread thrLinks = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                            String timest = currentDate + " " + currentTime;

                            String body = "{" +
                                    "\"timest\":\"" + timest + "\"," +
                                    "\"naam\":\"" + bedlicher.getStrNaam() + "\"," +
                                    "\"datum\":\"" + bedlicher.getDtmDatum() + "\"," +
                                    "\"kant\":\"L\"" +
                                    "}";
                            SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/post", body);
                        }
                    });
                    if (bluetoothSend.isConnected()) {
                        String naam = bedlicher.getStrNaam();
                        String datum = bedlicher.getDtmDatum().toString();
                        bluetoothSend.send("22");
                        if (!bluetoothSend.getBluetooth().contains("2")) {
                            Toast.makeText(getContext().getApplicationContext(), "Draaien niet gelukt", Toast.LENGTH_SHORT).show();
                            thrLinks.start();
                            thrLinks.join();
                        }
                    }
                } catch (IOException e) {
                    Log.e("IOExeptoin", e.getMessage());
                } catch (NullPointerException e) {
                    Log.e("NullPointerException", e.getMessage());
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
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