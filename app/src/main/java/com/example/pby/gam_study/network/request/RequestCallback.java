package com.example.pby.gam_study.network.request;

import com.example.pby.gam_study.network.response.Response;

public interface RequestCallback<T> {
    void onResult(Response<T> response);
}
