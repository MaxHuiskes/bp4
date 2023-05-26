package com.example.porgamring.helpers;

import android.widget.Spinner;

public class MySpinner {
    /**
     * function to select certain value in spinner
     */
    public static void selectSpinnerValue(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
