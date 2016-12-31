package com.nextbest.skalkowski.whatwedo.serwer_connections;

import com.nextbest.skalkowski.whatwedo.data_model.SetUserPreference;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface PreferenceService {
    @POST("setUserPreference")
    Call<StandardResponse> setUserPreference(@Body SetUserPreference setUserPreference);
}
