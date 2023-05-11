package com.example.porgamring.model;

public class VereisteVoorraad {
    private String strBarCode;
    private int Aantal;

    public String getStrBarCode() {
        return strBarCode;
    }

    public void setStrBarCode(String strBarCode) {
        this.strBarCode = strBarCode;
    }

    public int getAantal() {
        return Aantal;
    }

    public void setAantal(int aantal) {
        Aantal = aantal;
    }

    @Override
    public String toString() {
        return strBarCode;
    }
}
