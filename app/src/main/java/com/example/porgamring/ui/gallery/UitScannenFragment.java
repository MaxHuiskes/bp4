package com.example.porgamring.ui.gallery;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.APIHandler;
import com.example.porgamring.SentAPI;
import com.example.porgamring.databinding.FragmentUitScannenBinding;
import com.example.porgamring.model.ProductBarcode;
import com.example.porgamring.model.TransactieVoorraad;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UitScannenFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private FragmentUitScannenBinding binding;
    private SurfaceView surfaceViewUit;
    private BarcodeDetector barcodeDetectorUit;
    private ArrayList<ProductBarcode> barcodesList;
    private ArrayList<TransactieVoorraad> barcodeListver;
    private CameraSource cameraSourceUit;
    private TextView barcodeText;
    private String barcodeData;
    private Button btnUitScan;
    private int currentAantal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UitScannenViewModel galleryViewModel =
                new ViewModelProvider(this).get(UitScannenViewModel.class);

        binding = FragmentUitScannenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        surfaceViewUit = binding.surfaceView;

        barcodeText = binding.barcodeText;
        barcodesList = new ArrayList<>();

        btnUitScan = binding.btnUitScann;

        btnUitScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        APIHandler api = new APIHandler();
                        Log.i("enter", "enter getAlHups()");
                        Log.i("enter", "producten/get_barcode/" + barcodeData);
                        barcodesList = api.getAlHups("producten/get_barcode/" + barcodeData);
                        Log.i("leaving", "leave getAlHups()");

                    }
                });
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread thrver = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        APIHandler api = new APIHandler();
                        Log.i("enter", "enter getAlHups()");
                        Log.i("enter", "aantal/get/" + barcodeData);
                        barcodeListver = api.getAlTrans("aantal/get/" + barcodeData);
                        Log.i("leaving", "leave getAlver");
                        Log.i("barcodeListver", barcodeListver.toString());
                        for (TransactieVoorraad o : barcodeListver) {
                            Log.i("bool\t\t\t\t\t\t", String.valueOf(o.isBoolPos()));
                            if (o.isBoolPos() == 1) {
                                currentAantal = currentAantal + o.getAantal();
                                Log.i("\t\t\t\t", String.valueOf(currentAantal));
                            } else if (o.isBoolPos() == 0) {
                                currentAantal = currentAantal - o.getAantal();
                                Log.i("\t\t\t\t", String.valueOf(currentAantal));
                            }
                        }
                    }
                });

                try {
                    thrver.start();
                    thrver.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    if (barcodeData.equals(barcodesList.get(0).getStrBarcode())) {
                        Thread thrAdd = new Thread(new Runnable() {
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                            String timest = currentDate + " " + currentTime;

                            @Override
                            public void run() {
                                Log.i("strat", "start");
                                String body = "{\"barcode\":\"" + barcodeData
                                        + "\",\"timest\":\"" + timest
                                        + "\",\"aantal\":1"
                                        + ",\"pos\": 0}";
                                Log.i("body", body);
                                SentAPI.post("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/aantal/insert"
                                        , body);
                            }
                        });
                        if (currentAantal >= 1) {
                            thrAdd.start();
                            thrAdd.join();
                            //database aantal aanwezig -1
                            Toast.makeText(getActivity().getApplicationContext(), "Product succesful verwijderd", Toast.LENGTH_LONG).show();
                        } else {
                            throw new RuntimeException("Er zijn geen producten meer.");
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Product niet gevonden", Toast.LENGTH_LONG).show();

                }
            }

        });

        return root;
    }


    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetectorUit = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSourceUit = new CameraSource.Builder(getActivity(), barcodeDetectorUit)
                .setRequestedPreviewSize(408, 322)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceViewUit.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSourceUit.start(surfaceViewUit.getHolder());
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
                cameraSourceUit.stop();
            }
        });


        barcodeDetectorUit.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }

                    });

                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSourceUit.release();
    }
}