package com.ugr.farmaciads.ui.carrito;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarritoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarritoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CARRITO fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}