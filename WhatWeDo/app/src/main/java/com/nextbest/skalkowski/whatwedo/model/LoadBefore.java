package com.nextbest.skalkowski.whatwedo.model;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.DeviceToken;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;

public class LoadBefore implements GetResponse {
    private GetResponse getResponse;

    private static final String ACTION_GET_USER = "action_get_user";
    private static final String ACTION_SET_DEVICE_TOKEN = "action_set_device_token";
    private String action;

    public LoadBefore(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    public void loginToApp(String action) {
        this.action = action;
        UserActions userActions = new UserActions(this);
        userActions.getUser(ACTION_GET_USER);
        userActions.setUserDeviceToken(new DeviceToken(FirebaseInstanceId.getInstance().getToken())
                , ACTION_SET_DEVICE_TOKEN);


    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_GET_USER)) {
            this.getResponse.getResponseSuccess(0, this.action);
        }

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