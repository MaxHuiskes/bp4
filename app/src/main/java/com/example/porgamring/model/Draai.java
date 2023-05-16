package com.example.porgamring.model;

public class Draai {
    private Persoon persoon;
    private String dtmMoment;
    private String strKant;

    public Draai(Persoon persoon, String dtmMoment, String strKant) {
        this.dtmMoment = dtmMoment;
        this.strKant = strKant;
        this.persoon = persoon;
    }

    public Draai() {
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
        String time = dtmMoment.substring(dtmMoment.indexOf('T') + 1, dtmMoment.length() - 1);
        String datum = dtmMoment.substring(0, dtmMoment.indexOf('T'));
        this.dtmMoment = datum + " " + time;
    }

    public String getStrKant() {
        return strKant;
    }

    public void setStrKant(String strKant) {
        if (strKant.equals("r") || strKant.equals("R")) {
            strKant = "Rechts";
        } else if (strKant.equals("l") || strKant.equals("L")) {
            strKant = "Links";
        }
        this.strKant = strKant;
    }

    @Override
    public String toString() {
        return "Naar " +strKant + " op " + dtmMoment;
    }
}
