package com.nextbest.skalkowski.whatwedo.serwer_connections;

import com.nextbest.skalkowski.whatwedo.data_model.DeviceToken;
import com.nextbest.skalkowski.whatwedo.data_model.GetUserResponse;
import com.nextbest.skalkowski.whatwedo.data_model.SearchUser;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogin;
import com.nextbest.skalkowski.whatwedo.data_model.UserLoginResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogoutResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserRegister;
import com.nextbest.skalkowski.whatwedo.data_model.UserSearchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface UserService {

    @POST("login")
    Call<UserLoginResponse> login(@Body UserLogin userLogin);

    @POST("register")
    Call<UserLoginResponse> register(@Body UserRegister userRegister);

    @POST("socialLogin")
    Call<UserLoginResponse> socialLogin(@Body UserLogin userLogin);

    @GET("logout")
    Call<UserLogoutResponse> logout();

    @GET("getMyUser")
    Call<GetUserResponse> getUser();

    @POST("setUserDeviceToken")
    Call<StandardResponse> setUserDeviceToken(@Body DeviceToken deviceToken);

    @POST("findUser")
    Call<UserSearchResponse> findUser(@Body SearchUser searchUser);

}
