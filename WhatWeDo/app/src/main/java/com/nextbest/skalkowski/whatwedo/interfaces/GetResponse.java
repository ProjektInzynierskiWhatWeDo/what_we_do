package com.nextbest.skalkowski.whatwedo.interfaces;

public interface GetResponse {
    void getResponseSuccess(Object object, String action);

    void getResponseFail(Object object, String action);

    void getResponseServerFail(Object object, String action);

    void getResponseTokenExpired();
}
