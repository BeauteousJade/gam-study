package com.example.pby.gam_study.network.request;

public interface Request<T> {
    void enqueue(RequestCallback<T> requestCallback);

    default void enqueue() {
        enqueue(null);
    }

    void cancel();
}