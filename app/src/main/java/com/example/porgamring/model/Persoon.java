package com.example.porgamring.model;

import java.time.LocalDate;

public class Persoon {

    private String strNaam;
    private LocalDate dtmDatum;
    private int intGewicht;
    private String strBloedgroep;

    public Persoon( String strNaam, LocalDate dtmDatum, int intGewicht, String strBloedgroep){
        this.dtmDatum=dtmDatum;
        this.strBloedgroep = strBloedgroep;
        this.intGewicht = intGewicht;
        this.strNaam = strNaam;

    }

    public LocalDate getDtmDatum() {
        return dtmDatum;
    }

    public void setDtmDatum(LocalDate dtmDatum) {
        this.dtmDatum = dtmDatum;
    }

    public String getStrNaam() {
        return strNaam;
    }

    public void setStrNaam(String strNaam) {
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
