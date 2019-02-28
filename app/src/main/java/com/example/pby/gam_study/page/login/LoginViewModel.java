package com.example.pby.gam_study.page.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mValidateCode = new MutableLiveData<>();


    public MutableLiveData<String> getValidateCode() {
        return mValidateCode;
    }
}
