package com.example.porgamring.helpers;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.example.porgamring.model.Component;
import com.example.porgamring.model.Draai;
import com.example.porgamring.model.Persoon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class APIHandler {
    private final StringBuilder stringBuilder = new StringBuilder();
    private final JSONBuilder jsonBuilder = new JSONBuilder();

    public String getProducten(String data) {
        //StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);
        try {
            // url to json file
            URL url
                    = new URL(data);
            //Log.i("urldone", url.toString());
            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();
            //Log.i("HttpURLConnection", connection.toString());
            connection.setRequestMethod("GET");
            connection.connect();

            int responsecode = connection.getResponseCode();
            //Log.i("Resposecode", String.valueOf(responsecode));
            if (responsecode != 200) {
                throw new RuntimeException("RersponseCode: " + responsecode);
            } else {
                // scans for data in json file until there is no more data
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                    //Log.i("stringbuilder", stringBuilder.toString());
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

    public ArrayList<Persoon> getAlHups(String api) {
        // gets al data from the json file and put them in object list
        ArrayList<Persoon> hups;
        String s = getProducten(api);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildHups(s);
        return hups;
    }

    public ArrayList<Draai> getAlVereiste(String apid) {
        // gets al data from the json file and put them in object list
        ArrayList<Draai> hups;
        String s = getProducten(apid);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildVereisete(s);
        return hups;
    }

    public ArrayList<Component> getAlTrans(String api) {
        // gets al data from the json file and put them in object list
        ArrayList<Component> hups;
        String s = getProducten(api);
        Log.i("JSON file", s);
        hups = jsonBuilder.buildTrans(s);
        return hups;
    }

}
