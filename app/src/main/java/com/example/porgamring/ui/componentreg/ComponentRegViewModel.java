package com.example.porgamring.ui.componentreg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ComponentRegViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ComponentRegViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}