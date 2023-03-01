package com.example.porgamring.ui.uit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UitScannenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UitScannenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}