package com.nextbest.skalkowski.whatwedo.actions;

import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.DeviceToken;
import com.nextbest.skalkowski.whatwedo.data_model.GetUserResponse;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogin;
import com.nextbest.skalkowski.whatwedo.data_model.UserLoginResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogoutResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;
import com.nextbest.skalkowski.whatwedo.data_model.UserRegister;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.nextbest.skalkowski.whatwedo.local_database.Token;
import com.nextbest.skalkowski.whatwedo.serwer_connections.ServiceGenerator;
import com.nextbest.skalkowski.whatwedo.serwer_connections.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActions extends Action {

    private GetResponse getResponse;
    private static final int STATUS_SUCCESS = 1;
    private static final int LOGGED_BY_APP = 0;
    private static final int STATUS_DATA_ERROR = 10;

    public UserActions(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    //fixme gotowe
    public void loginToApp(UserLogin userLogin, final String action) {
        UserService userService = ServiceGenerator.createService(UserService.class);
        Call<UserLoginResponse> call = userService.login(userLogin);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.body() instanceof UserLoginResponse) {
                    if (response.body().getStatus() == STATUS_SUCCESS) {
                        Token.deleteAll(Token.class);
                        new Token(response.body().getMessage(), LOGGED_BY_APP).save();
                        getResponse.getResponseSuccess(response.body(), action);
                    } else if (response.body().getStatus() == STATUS_DATA_ERROR) {
                        getResponse.getResponseFail(response.body().getMessage(), action);
                    } else {
                        getResponse.getResponseServerFail(R.string.connectionAppError, action);
                    }
                } else {
                    Log.d("QWERTY", "onResponse: " + response.message().toString());
                    getResponse.getResponseServerFail(R.string.connectionAppError, action);
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("MODEL", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    //fixme gotowe
    public void registerToApp(UserRegister userRegister, final String action) {
        UserService userService = ServiceGenerator.createService(UserService.class);
        Call<UserLoginResponse> call = userService.register(userRegister);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.body() instanceof UserLoginResponse) {
                    if (response.body().getStatus() == STATUS_SUCCESS) {
                        Token.delete(Token.class);
                        new Token(response.body().getMessage(), LOGGED_BY_APP).save();
                        getResponse.getResponseSuccess(0, action);
                    } else if (response.body().getStatus() == STATUS_DATA_ERROR) {
                        getResponse.getResponseFail(response.body().getMessage(), action);
                    } else {
                        getResponse.getResponseServerFail(R.string.connectionAppErrorRegister, action);
                    }
                } else {
                    getResponse.getResponseServerFail(R.string.connectionAppErrorRegister, action);
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("MODEL", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    //fixme gotowe
    public void socialLoginToApp(UserLogin userLogin, final String action) {
        UserService userService = ServiceGenerator.createService(UserService.class);
        Call<UserLoginResponse> call = userService.socialLogin(userLogin);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    Token.deleteAll(Token.class);
                    new Token(response.body().getMessage(), LOGGED_BY_APP).save();
                    getResponse.getResponseSuccess(response.body(), action);
                } else {
                    Log.d("MODEL", "onResponse: " + response.message());
                    getResponse.getResponseServerFail(R.string.connectionAppError, action);
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("MODEL", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    public void logout(final String action) {
        UserService userService = ServiceGenerator.createServiceToken(UserService.class);
        Call<UserLogoutResponse> userLogoutResponseCall = userService.logout();
        userLogoutResponseCall.enqueue(new Callback<UserLogoutResponse>() {
            @Override
            public void onResponse(Call<UserLogoutResponse> call, Response<UserLogoutResponse> response) {
                if (response.isSuccessful()) {
                    getResponse.getResponseSuccess(action, action);
                } else {
                    if (response.code() == HTTP_RESPONSE_UNAUTHORIZED) {
                        getResponse.getResponseSuccess(action, action);
                    } else {
                        getResponse.getResponseServerFail(R.string.serverError, action);
                    }

                }
            }

            @Override
            public void onFailure(Call<UserLogoutResponse> call, Throwable t) {
                Log.d("MODEL", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    public void getUser(final String action) {
        UserService userService = ServiceGenerator.createServiceToken(UserService.class);
        Call<GetUserResponse> getUserResponseCall = userService.getUser();
        getUserResponseCall.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    GetUserResponse getUserResponse = response.body();
                    LoggedUser.deleteLoggedUser();
                    LoggedUser loggedUser = new LoggedUser();
                    loggedUser.setIdUser(getUserResponse.getId());
                    loggedUser.setEmail(getUserResponse.getEmail());
                    loggedUser.setName(getUserResponse.getName());
                    loggedUser.setImage(getUserResponse.getUser_image());
                    loggedUser.save();
                    UserPreference.deleteAll(UserPreference.class);
                    UserPreference.saveInTx(response.body().getUserPreferences());
                    getResponse.getResponseSuccess(0, action);
                } else {
                    errorServerResponse();
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                Log.d("Action", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }


    private void errorServerResponse() {

    }

    public void setUserDeviceToken(DeviceToken userDeviceToken , final String action){
        UserService userService = ServiceGenerator.createServiceToken(UserService.class);
        Call<StandardResponse> standardResponseCall = userService.setUserDeviceToken(userDeviceToken);
        standardResponseCall.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                getResponse.getResponseSuccess(action,action);
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d("Action", "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }


}
