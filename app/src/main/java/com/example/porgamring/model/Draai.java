package com.example.porgamring.model;

public class Draai {
    Persoon persoon;
    String dtmMoment;
    String strKant;

    public Draai(Persoon persoon, String dtmMoment, String strKant) {
        this.dtmMoment = dtmMoment;
        this.strKant = strKant;
        this.persoon = persoon;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public String getDtmMoment() {
        return dtmMoment;
    }

    public void setDtmMoment(String dtmMoment) {
        this.dtmMoment = dtmMoment;
    }

    public String getStrKant() {
        return strKant;
    }

    public void setStrKant(String strKant) {
        this.strKant = strKant;
    }

    @Override
    public String toString() {
        return strKant + ' ' + dtmMoment;
    }
}
