package com.example.porgamring;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.porgamring.helpers.APIHandler;
import com.example.porgamring.model.Component;
import com.example.porgamring.model.Draai;
import com.example.porgamring.model.Persoon;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
    * Unit test to test to dtmDatum in class Person
    */
    @Test
    public void testPersoonDatum(){
        Persoon p = new Persoon();
        p.setDtmDatum("2000-10-28T00:00:00Z");
        assertEquals("2000-10-28",p.getDtmDatum());
    }

    /**
     * Unit test to test to naam in class Person
     */
    @Test
    public void testPersoonNaam(){
        Persoon p = new Persoon();
        p.setStrNaam("Max Huiskes");
        assertEquals("Max Huiskes",p.getStrNaam());
    }

    /**
     * Unit test to test to Persoon in class Draai
     */
    @Test
    public void testDraaiPersoon(){
        Draai d = new Draai();
        Persoon p = new Persoon("Max Huiskes","2000-10-28T00:00:00Z",83,"A+");
        d.setPersoon(p);
        assertEquals(p,d.getPersoon());
    }

    /**
     * Unit test to test to Persoon in class Component
     */
    @Test
    public void testComponentPersoon(){
        Component c = new Component();
        Persoon p = new Persoon("Max Huiskes","2000-10-28T00:00:00Z",83,"A+");
        c.setPersoon(p);
        assertEquals(p,c.getPersoon());
    }

    /**
     * Unit test to test api json data to string in class APIHandler
     */
    @Test
    public void testAPIHandler(){
        APIHandler a = new APIHandler();
        String json = a.getProducten("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28");
        String expected = "{\"items\":[{\"strnaam\":\"max\",\"dtmdatum\":\"2000-10-28T00:00:00Z\",\"intgewicht\":82,\"strbloedgroep\":\"a+\"}],\"hasMore\":false,\"limit\":25,\"offset\":0,\"count\":1,\"links\":[{\"rel\":\"self\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28\"},{\"rel\":\"describedby\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/metadata-catalog/persoon/get/max/item\"},{\"rel\":\"first\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28\"}]}";
        assertEquals(expected,json);
    }

    /**
     * Intergratie test van klasse Draai en Regressie test van klasse Component
     */
    @Test
    public void testComponentDraai(){
        Component c = new Component();
        Persoon p = new Persoon("Max Huiskes","2000-10-28T00:00:00Z",83,"A+");
        c.setPersoon(p);
        assertEquals(p,c.getPersoon());
        Draai d = new Draai();
        d.setPersoon(p);
        assertEquals(p,d.getPersoon());
    }

    /**
     * Intergratie test van klasse Draai en Regressie test van klasse Persoon
     */
    @Test
    public void testPersoonDraai(){
        Persoon p = new Persoon();
        p.setStrNaam("Max Huiskes");
        p.setDtmDatum("2000-10-28T00:00:00Z");
        p.setStrBloedgroep("A+");
        p.setIntGewicht(3);
        assertEquals("Max Huiskes",p.getStrNaam());
        assertEquals("2000-10-28",p.getDtmDatum());
        assertEquals("A+",p.getStrBloedgroep());
        assertEquals(3,p.getIntGewicht());

        Draai d = new Draai();
        d.setPersoon(p);
        assertEquals(p, d.getPersoon());
    }

    /**
     * Intergratie test van klasse APIHandler en Regressie test van klasse Persoon
     */
    @Test
    public void testPesoonAPIHandler(){
        Persoon p = new Persoon();
        p.setStrNaam("Max Huiskes");
        p.setDtmDatum("2000-10-28T00:00:00Z");
        p.setStrBloedgroep("A+");
        p.setIntGewicht(3);
        assertEquals("Max Huiskes",p.getStrNaam());
        assertEquals("2000-10-28",p.getDtmDatum());
        assertEquals("A+",p.getStrBloedgroep());
        assertEquals(3,p.getIntGewicht());

        APIHandler a = new APIHandler();
        String json = a.getProducten("https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28");
        String expected = "{\"items\":[{\"strnaam\":\"max\",\"dtmdatum\":\"2000-10-28T00:00:00Z\",\"intgewicht\":82,\"strbloedgroep\":\"a+\"}],\"hasMore\":false,\"limit\":25,\"offset\":0,\"count\":1,\"links\":[{\"rel\":\"self\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28\"},{\"rel\":\"describedby\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/metadata-catalog/persoon/get/max/item\"},{\"rel\":\"first\",\"href\":\"https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/persoon/get/max/2000-10-28\"}]}";
        assertEquals(expected,json);
    }
}