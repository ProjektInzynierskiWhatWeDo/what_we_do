package com.nextbest.skalkowski.whatwedo.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.DeviceToken;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Token;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService implements GetResponse {

    private static final String ACTION_SEND_DEVICE_TOKEN = "action_send_device_token";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        if (Token.isToken()) {
            UserActions userActions = new UserActions(this);
            userActions.setUserDeviceToken(new DeviceToken(token), ACTION_SEND_DEVICE_TOKEN);
        }
    }

    @Override
    public void getResponseSuccess(Object object, String action) {

    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {

    }

    @Override
    public void getResponseTokenExpired() {

    }
}
