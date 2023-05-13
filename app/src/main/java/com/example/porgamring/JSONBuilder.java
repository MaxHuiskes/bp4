package com.example.porgamring;

import android.util.Log;

import com.example.porgamring.model.Persoon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JSONBuilder {

    public ArrayList<Persoon> buildHups(String data) {
        ArrayList<Persoon> hubs = new ArrayList();
       // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String naam = (userDetail.getString("strnaam"));
                String dtmDatum = (userDetail.getString("dtmdatum"));
                int gewicht = Integer.parseInt (userDetail.getString("intgewicht"));
                String strbloedgroep = (userDetail.getString("strbloedgroep"));

                Persoon h = new Persoon();
                h.setStrNaam(naam);
                h.setDtmDatum(dtmDatum);
                h.setStrGewicht(gewicht);
                h.setStrBloedgroep(strbloedgroep);
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }
    /*public ArrayList<VereisteVoorraad> buildVereisete(String data) {
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
