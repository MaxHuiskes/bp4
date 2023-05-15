package com.example.porgamring.model;

import android.util.Log;

public class Persoon {

    private String strNaam;
    private String dtmDatum;
    private int intGewicht;
    private String strBloedgroep;

    public Persoon(String strNaam, String dtmDatum, int intGewicht, String strBloedgroep) {
        try {
            dtmDatum = dtmDatum.substring(0, dtmDatum.indexOf('T'));
        } catch (Exception e) {
            Log.e("Exception",e.getMessage());
        }
        this.dtmDatum = dtmDatum;
        this.strBloedgroep = strBloedgroep;
        this.intGewicht = intGewicht;
        this.strNaam = strNaam;

    }

    public Persoon() {
    }

    public String getDtmDatum() {
        return dtmDatum;
    }

    public void setDtmDatum(String dtmDatum) {
        dtmDatum = dtmDatum.substring(0, dtmDatum.indexOf('T'));

        this.dtmDatum = dtmDatum;
    }

    public String getStrNaam() {
        return strNaam;
    }

    public void setStrNaam(String strNaam) {
        Log.e("naam\t\t", strNaam);
        this.strNaam = strNaam;
    }

    public int getIntGewicht() {
        return intGewicht;
    }

    public void setIntGewicht(int intGewicht) {
        this.intGewicht = intGewicht;
    }

    public String getStrBloedgroep() {
        return strBloedgroep;
    }

    public void setStrBloedgroep(String strBloedgroep) {
        this.strBloedgroep = strBloedgroep;
    }

    @Override
    public String toString() {
        return strNaam + ' ' + dtmDatum;
    }
}
