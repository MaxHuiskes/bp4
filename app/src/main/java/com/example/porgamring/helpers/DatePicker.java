package com.example.porgamring.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.Button;

import java.util.Calendar;

public class DatePicker {
    public DatePickerDialog datePickerDialog;
    private final Button dateButton;
    private final Activity getActivity;

    public DatePicker(Button dateButton, Activity getActivity) {
        this.dateButton = dateButton;
        this.getActivity = getActivity;
    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void initDatePicker(boolean future) {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity, style, dateSetListener, year, month, day);
        if (!future) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }

    }

    private String makeDateString(int day, int month, int year) {
        return day + "-" + month + "-" + year;
    }
}
