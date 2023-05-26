package com.example.porgamring.helpers;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class SentAPI {
    public static String post(String completeUrl, String body) {
        URL url;
        int responsecode = 0;
        try {
            url = new URL(completeUrl);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            byte[] out = body.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();

            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
                Log.i("succes", "");
            } catch (Exception e) {
                Log.i("Exception", e.toString());
            }
            responsecode = http.getResponseCode();
            Log.i("Resposecode", String.valueOf(responsecode));
        } catch (MalformedURLException e) {
            Log.i("MalformedURLException", e.toString());
        } catch (ProtocolException e) {
            Log.i("ProtocolException", e.toString());
        } catch (IOException e) {
            Log.i("IOException", e.toString());
        }
        return String.valueOf(responsecode);
    }

    public static String put(String completeUrl, String body) {
        URL url;
        int responsecode = 0;
        try {
            url = new URL(completeUrl);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("PUT"); // PUT is another valid option
            http.setDoOutput(true);
            byte[] out = body.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();

            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
                Log.i("succes", "");
            } catch (Exception e) {
                Log.i("Exception", e.toString());
            }
            responsecode = http.getResponseCode();
            Log.i("Resposecode", String.valueOf(responsecode));
        } catch (MalformedURLException e) {
            Log.i("MalformedURLException", e.toString());
        } catch (ProtocolException e) {
            Log.i("ProtocolException", e.toString());
        } catch (IOException e) {
            Log.i("IOException", e.toString());
        }
        return String.valueOf(responsecode);
    }

    public static String delete(String urlComplete) throws IOException {
        URL url;
        int responsecode = 0;
        try {
            url = new URL(urlComplete);
            Log.i("urldone", url.toString());
            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();
            Log.i("HttpURLConnection", connection.toString());
            connection.setRequestMethod("DELETE");
            connection.connect();

            responsecode = connection.getResponseCode();
            Log.i("Resposecode", String.valueOf(responsecode));
            if (responsecode != 200) {
                throw new RuntimeException("RersponseCode: " + responsecode);
            }
        } catch (ProtocolException e) {
            Log.e("ProtocolException", e.toString());
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        return String.valueOf(responsecode);
    }
}
