package com.nextbest.skalkowski.whatwedo.serwer_connections;

import com.nextbest.skalkowski.whatwedo.data_model.DeleteGroup;
import com.nextbest.skalkowski.whatwedo.data_model.EditGroup;
import com.nextbest.skalkowski.whatwedo.data_model.Group;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupService {
    @POST("addGroup")
    Call<StandardResponse> addGroup(@Body Group group);

    @POST("editGroup")
    Call<StandardResponse> editGroup(@Body EditGroup editGroup);

    @POST("deleteGroup")
    Call<StandardResponse> deleteGroup(@Body DeleteGroup deleteGroup);
}
