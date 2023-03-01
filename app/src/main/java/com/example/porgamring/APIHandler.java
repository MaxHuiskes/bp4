package com.example.porgamring;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.example.porgamring.model.ProductBarcode;
import com.example.porgamring.model.TransactieVoorraad;
import com.example.porgamring.model.VereisteVoorraad;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class APIHandler {
    private final String apiurl = "https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/";
    private StringBuilder stringBuilder = new StringBuilder();
    private JSONBuilder jsonBuilder = new JSONBuilder();

    public String getProducten(String data) {
        String urlapi = apiurl + data;

        try {
            // url to json file
            URL url
                    = new URL(urlapi);
            Log.i("urldone", url.toString());
            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();
            Log.i("HttpURLConnection", connection.toString());
            connection.setRequestMethod("GET");
            connection.connect();

            int responsecode = connection.getResponseCode();
            Log.i("Resposecode", String.valueOf(responsecode));
            if (responsecode != 200) {
                throw new RuntimeException("RersponseCode: " + responsecode);
            } else {
                // scans for data in json file until there is no more data
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                    Log.i("stringbuilder", stringBuilder.toString());
                }
                scanner.close();
            }
        } catch (IOException e) {
            Log.i("ioexeption", e.toString());
        } catch (NetworkOnMainThreadException e) {
            Log.i("NetworkOnMainThreadException", e.toString());
        } catch (RuntimeException e) {
            Log.i("runtimeexeption", e.toString());
        } catch (Exception e) {
            Log.i("exeption", e.toString());
        }
        return stringBuilder.toString();
    }

    public ArrayList<ProductBarcode> getAlHups(String api) {
        // gets al data from the json file and put them in object list
        ArrayList<ProductBarcode> hups = new ArrayList<ProductBarcode>();
        String s = getProducten(api);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildHups(s);
        return hups;
    }
    public ArrayList<VereisteVoorraad> getAlVereiste(String api) {
        // gets al data from the json file and put them in object list
        ArrayList<VereisteVoorraad> hups = new ArrayList<VereisteVoorraad>();
        String s = getProducten(api);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildVereisete(s);
        return hups;
    }
    public ArrayList<TransactieVoorraad> getAlTrans(String api) {
        // gets al data from the json file and put them in object list
        ArrayList<TransactieVoorraad> hups = new ArrayList<TransactieVoorraad>();
        String s = getProducten(api);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildTrans(s);
        return hups;
    }

}
