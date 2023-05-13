package com.example.porgamring.model;

import android.util.Log;

import java.time.LocalDate;

public class Persoon {

    private String strNaam;
    private String dtmDatum;
    private int intGewicht;
    private String strBloedgroep;

    public Persoon(String strNaam, String dtmDatum, int intGewicht, String strBloedgroep) {
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
        dtmDatum = dtmDatum.substring(0,dtmDatum.indexOf('T'));

        this.dtmDatum = dtmDatum;
    }

    public String getStrNaam() {
        return strNaam;
    }

    public void setStrNaam(String strNaam) {
        Log.e("naam\t\t",strNaam);
        this.strNaam = strNaam;
    }

    public int getStrGewicht() {
        return intGewicht;
    }

    public void setStrGewicht(int Gewicht) {
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
