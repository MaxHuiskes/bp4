package com.example.porgamring.ui.invoerReg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InRegScannenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InRegScannenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}