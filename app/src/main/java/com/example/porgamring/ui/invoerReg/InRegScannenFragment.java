package com.example.porgamring.ui.invoerReg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.APIHandler;
import com.example.porgamring.SentAPI;
import com.example.porgamring.databinding.FragmentInRegScannenBinding;
import com.example.porgamring.model.ProductBarcode;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InRegScannenFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private FragmentInRegScannenBinding binding;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private List<String> barcodesList;
    private CameraSource cameraSource;
    private TextView barcodeText;
    private String barcodeData;
    private ArrayList<ProductBarcode> dataArrayList;
    private Button uitScan;
    private EditText aantal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InRegScannenViewModel slideshowViewModel =
                new ViewModelProvider(this).get(InRegScannenViewModel.class);

        binding = FragmentInRegScannenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        surfaceView = binding.surfaceView;
        barcodeText = binding.barcodeText;
        barcodesList = new ArrayList<>();
        barcodesList.add("2222");
        aantal = binding.aantalet;

        uitScan = binding.btnUitScannen;
        uitScan.setText("Scan");//set the text on button

        uitScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        APIHandler api = new APIHandler();
                        Log.i("enter", "enter getAlHups()");
                        Log.i("enter", "producten/get_barcode/" + barcodeData);
                        dataArrayList = api.getAlHups("producten/get_barcode/" + barcodeData);
                        Log.i("leaving", "leave getAlHups()");
                    }
                });
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (barcodeData.equals(dataArrayList.get(0).getStrBarcode())) {
                        Thread thrAdd = new Thread(new Runnable() {
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                            String timest = currentDate + " "+ currentTime;

                            @Override
                            public void run() {
                                Log.i("strat","start");
                                Log.i("timest", timest);
                                Log.i("aantal", aantal.getText().toString());
                                String body = "{\"barcode\":\""+barcodeData
                                        +"\",\"timest\":\""+timest
                                        +"\",\"aantal\":"+ aantal.getText()
                                        +",\"pos\": 1}";
                                Log.i("body", body);
                                SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/aantal/insert"
                                        ,body);
                            }
                        });
                        thrAdd.start();
                        thrAdd.join();
                    }

                } catch (Exception e) {
                    if (!Objects.equals(barcodeData, "Barcode Text") || !barcodeData.isEmpty()) {
                        Intent i = new Intent(getActivity(), RegenstratieActivity.class);
                        i.putExtra("barcode", barcodeText.getText());
                        i.putExtra("aantal",aantal.getText());
                        try {
                            startActivity(i);
                        } catch (Exception ex) {
                            Log.i("", ex.getMessage());
                        }
                    } else {
                        Log.i("wrong barcode", e.getMessage());
                    }
                    if (!e.getMessage().isEmpty()){
                        Log.i("wrong barcode", e.getMessage());
                    }
                }

            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onStart() {
        super.onStart();
        initialiseDetectorsAndSources();
    }

    private void initialiseDetectorsAndSources() {

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(408, 322)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //Toast.makeText(getActivity().getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    barcodeText.post(new Runnable() {
                        @Override
                        public void run() {

                            barcodeData = barcodes.valueAt(0).displayValue;
                            barcodeText.setText(barcodeData);
                            for (String p : barcodesList)
                                if (Objects.equals(p, barcodeData)) {
                                    //database aantal aanwezig + 1
                                }
                        }
                    });
                }
            }
        });
    }
}