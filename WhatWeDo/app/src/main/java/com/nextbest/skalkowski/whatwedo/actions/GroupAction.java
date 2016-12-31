package com.nextbest.skalkowski.whatwedo.actions;


import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.DeleteGroup;
import com.nextbest.skalkowski.whatwedo.data_model.Group;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserLoginResponse;
import com.nextbest.skalkowski.whatwedo.fragments.AddGroup;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.serwer_connections.GroupService;
import com.nextbest.skalkowski.whatwedo.serwer_connections.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAction extends Action {
    private GetResponse getResponse;

    public GroupAction(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    //fixme good
    public void addGroup(Group group, final String action) {
        GroupService groupService = ServiceGenerator.createServiceToken(GroupService.class);
        Call<StandardResponse> call = groupService.addGroup(group);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    getResponse.getResponseSuccess(R.string.successfullCreateGroup, action);
                } else {
                    if (response.code() == HTTP_RESPONSE_UNAUTHORIZED) {
                        getResponse.getResponseTokenExpired();
                    } else {
                        getResponse.getResponseServerFail(R.string.serverError, action);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(getClass().getName(), "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    public void editGroup(Group group, final String action) {
        GroupService groupService = ServiceGenerator.createServiceToken(GroupService.class);
        Call<StandardResponse> call = groupService.editGroup(group);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d(getClass().getName(), "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    public void deleteGroup(DeleteGroup deleteGroup, final String action) {
        GroupService groupService = ServiceGenerator.createServiceToken(GroupService.class);
        Call<StandardResponse> call = groupService.deleteGroup(deleteGroup);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {

                } else {

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
