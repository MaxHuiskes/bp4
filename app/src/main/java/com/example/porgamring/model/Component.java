package com.example.porgamring.model;

public class Component {
    private Persoon persoon;
    private String strModelNummer;
    private String strProduct;

    public Component(Persoon persoon, String strModelNummer, String strProduct) {
        this.strModelNummer = strModelNummer;
        this.strProduct = strProduct;
        this.persoon = persoon;
    }

    public Component() {

    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public String getStrModelNummer() {
        return strModelNummer;
    }

    public void setStrModelNummer(String strModelNummer) {
        this.strModelNummer = strModelNummer;
    }

    public String getStrProduct() {
        return strProduct;
    }

    public void setStrProduct(String strProduct) {
        this.strProduct = strProduct;
    }

    @Override
    public String toString() {
        return "Product:" + strProduct + " Modelnummer:" + strModelNummer;
    }
}
