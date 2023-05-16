package com.example.porgamring;

import android.util.Log;

import com.example.porgamring.model.Component;
import com.example.porgamring.model.Draai;
import com.example.porgamring.model.Persoon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                int gewicht = Integer.parseInt(userDetail.getString("intgewicht"));
                Log.e("json gewicht", String.valueOf(gewicht));
                String strbloedgroep = (userDetail.getString("strbloedgroep"));
                Log.e("bloed", strbloedgroep);

                Persoon h = new Persoon();
                h.setStrNaam(naam);
                h.setDtmDatum(dtmDatum);
                h.setIntGewicht(gewicht);
                Log.e("gwicht opgelsgen", String.valueOf(h.getIntGewicht()));
                h.setStrBloedgroep(strbloedgroep);
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.e("PraseException", pe.getMessage());
        }
        return hubs;
    }

    public ArrayList<Draai> buildVereisete(String data) {
        ArrayList<Draai> hubs = new ArrayList();
        // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String moment = (userDetail.getString("dtmmoment"));
                String kant = (userDetail.getString("strkant"));
                String naam = (userDetail.getString("strnaam"));
                String datum = (userDetail.getString("dtmdatum"));

                Draai h = new Draai();
                h.setStrKant(kant);
                h.setDtmMoment(moment);
                h.setPersoon(new Persoon(naam, datum, 0, ""));
                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }

    public ArrayList<Component> buildTrans(String data) {
        ArrayList<Component> hubs = new ArrayList();
        // JSONParser parser = new JSONParser();
        try {
            Log.i("try-catch", "1");

            JSONObject json = new JSONObject(data);
            Log.i("try-catch", "2");
            JSONArray jaItems = (JSONArray) json.get("items");
            for (int i = 0; i < jaItems.length(); i++) {

                JSONObject userDetail = jaItems.getJSONObject(i);
                // fetch email and name and store it in arraylist
                String model = (userDetail.getString("strmodelnummer"));
                String naam = userDetail.getString("strnaam");
                String datum = (userDetail.getString("dtmdatum"));
                String product = userDetail.getString("strproduct");

                Component h = new Component();
                h.setPersoon(new Persoon(naam, datum, 0, ""));
                h.setStrModelNummer(model);
                h.setStrProduct(product);

                // adds current object to list of objects
                hubs.add(h);
            }
        } catch (JSONException pe) {
            Log.i("PraseException", pe.toString());
        }
        return hubs;
    }
}
