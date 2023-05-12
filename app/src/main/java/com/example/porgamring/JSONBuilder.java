package com.example.porgamring;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONBuilder {

   /* public ArrayList<ProductBarcode> buildHups(String data) {
        ArrayList<ProductBarcode> hubs = new ArrayList();
       // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String barcode = (userDetail.getString("strbarcode"));
                String product = (userDetail.getString("strproduct"));
                int inhoud = Integer.parseInt (userDetail.getString("intinhoud"));
                String eenheid = (userDetail.getString("streenheid"));

                ProductBarcode h = new ProductBarcode();
                h.setStrBarcode(barcode);
                h.setStrProduct(product);
                h.setIntInhoud(inhoud);
                h.setStrEenheid(eenheid);
                h.print();
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }
    public ArrayList<VereisteVoorraad> buildVereisete(String data) {
        ArrayList<VereisteVoorraad> hubs = new ArrayList();
       // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String barcode = (userDetail.getString("strbarcode"));
                int aantal = Integer.parseInt (userDetail.getString("intaantal"));

                VereisteVoorraad h = new VereisteVoorraad();
                h.setStrBarCode(barcode);
                h.setAantal(aantal);
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }
    public ArrayList<TransactieVoorraad> buildTrans(String data) {
        ArrayList<TransactieVoorraad> hubs = new ArrayList();
       // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String barcode = (userDetail.getString("strbarcode"));
                String dtmDatum =  userDetail.getString("dtmdatum");
                int aantal = Integer.parseInt (userDetail.getString("intaantal"));
                int bool = userDetail.getInt("boolpos");

                TransactieVoorraad h = new TransactieVoorraad();
                h.setStrBarcode(barcode);
                h.setBoolPos(bool);
                h.setDtmDatum(dtmDatum);
                h.setAantal(aantal);
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }*/
}
