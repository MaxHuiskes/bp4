package com.example.porgamring.model;

public class TransactieVoorraad {
    private String strBarcode;
    private String dtmDatum;
    private int intAantal;
    private int boolPos;

    public String getStrBarcode() {
        return strBarcode;
    }

    public void setStrBarcode(String strBarcode) {
        this.strBarcode = strBarcode;
    }

    public String getDtmDatum() {
        return dtmDatum;
    }

    public void setDtmDatum(String dtmDatum) {
        this.dtmDatum = dtmDatum;
    }

    public int getAantal() {
        return intAantal;
    }

    public void setAantal(int aantal) {
        this.intAantal = aantal;
    }

    public int isBoolPos() {
        return boolPos;
    }

    public void setBoolPos(int boolPos) {
        this.boolPos = boolPos;
    }

    @Override
    public String toString() {
        String substring = dtmDatum.substring(0, dtmDatum.indexOf('T'));
        String re = substring + " " + getAantal() + " ";
        if (boolPos == 1) {
            re = re + "toe gevoegd";
        } else {
            re = re + "verbruikt";
        }
        return re;
    }
}
