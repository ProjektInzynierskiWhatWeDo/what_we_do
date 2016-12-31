package com.nextbest.skalkowski.whatwedo.actions;

import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.GetUserResponse;
import com.nextbest.skalkowski.whatwedo.data_model.SetUserPreference;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;
import com.nextbest.skalkowski.whatwedo.serwer_connections.PreferenceService;
import com.nextbest.skalkowski.whatwedo.serwer_connections.ServiceGenerator;
import com.nextbest.skalkowski.whatwedo.serwer_connections.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PreferenceAction {

    private GetResponse getResponse;
    private static final int STATUS_SUCCESS = 1;
    private static final int LOGGED_BY_APP = 0;
    private static final int STATUS_DATA_ERROR = 10;

    public PreferenceAction(GetResponse getResponse) {
        this.getResponse = getResponse;
    }


    public void setUserPreference(final String action , SetUserPreference setUserPreference) {
        PreferenceService preferenceService = ServiceGenerator.createServiceToken(PreferenceService.class);
        Call<StandardResponse> setUserPreferenceCall = preferenceService.setUserPreference(setUserPreference);
        setUserPreferenceCall.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                 getResponse.getResponseSuccess(response.body(),action);
                } else {
                    getResponse.getResponseFail(0,action);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(getClass().getName(), "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }
}
