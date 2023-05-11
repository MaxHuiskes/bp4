package com.example.porgamring.model;

import android.util.Log;

public class ProductBarcode {
    private int intInhoud;
    private String strBarcode;
    private String strProduct;
    private String strEenheid;

    public int getIntInhoud() {
        return intInhoud;
    }

    public void setIntInhoud(int intInhoud) {
        this.intInhoud = intInhoud;
    }

    public String getStrBarcode() {
        return strBarcode;
    }

    public void setStrBarcode(String strBarcode) {
        this.strBarcode = strBarcode;
    }

    public String getStrEenheid() {
        return strEenheid;
    }

    public void setStrEenheid(String strEenheid) {
        this.strEenheid = strEenheid;
    }

    public String getStrProduct() {
        return strProduct;
    }

    public void setStrProduct(String strProduct) {
        this.strProduct = strProduct;
    }

    @Override
    public String toString() {
        String re = strBarcode + ' ' + strProduct + ' ' + intInhoud + ' ' + strEenheid;

        return re;
    }

    public void print() {

        Log.i("", strBarcode);
        Log.i("", strProduct);
        Log.i("", String.valueOf(intInhoud));
        Log.i("", strEenheid);
    }
}
