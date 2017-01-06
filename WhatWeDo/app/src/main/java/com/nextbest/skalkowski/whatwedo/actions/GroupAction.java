package com.nextbest.skalkowski.whatwedo.actions;


import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.DeleteGroup;
import com.nextbest.skalkowski.whatwedo.data_model.EditGroup;
import com.nextbest.skalkowski.whatwedo.data_model.Group;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.fragments.EventBusFragment;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.CustomEventBusMessage;
import com.nextbest.skalkowski.whatwedo.serwer_connections.GroupService;
import com.nextbest.skalkowski.whatwedo.serwer_connections.ServiceGenerator;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAction extends Action {
    private GetResponse getResponse;

    public GroupAction(GetResponse getResponse) {
        this.getResponse = getResponse;
    }
    public static final String ACTION_CHANGE_USER_GROUPS = "action_change_user_groups";


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

    public void editGroup(final EditGroup editGroup, final String action) {
        GroupService groupService = ServiceGenerator.createServiceToken(GroupService.class);
        Call<StandardResponse> call = groupService.editGroup(editGroup);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == STATUS_SUCCESS) {
                        UserGroups.editGroupName(editGroup.getId(), editGroup.getName());
                        refreshUserGroupsListView();
                        getResponse.getResponseSuccess(action, action);
                    } else {
                        getResponse.getResponseFail(response.body().getMessage(), action);
                    }
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


    public void deleteGroup(final DeleteGroup deleteGroup, final String action) {
        GroupService groupService = ServiceGenerator.createServiceToken(GroupService.class);
        Call<StandardResponse> call = groupService.deleteGroup(deleteGroup);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == STATUS_SUCCESS) {
                        //todo usunąć czat
                        UserGroups.deleteGroupById(deleteGroup.getId());
                        getResponse.getResponseSuccess(action, action);
                    } else {
                        getResponse.getResponseFail(response.body().getMessage(), action);
                    }
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

    public void refreshUserGroupsListView(){
        CustomEventBusMessage customEventBusMessage = new CustomEventBusMessage();
        customEventBusMessage.setCustomMessage(ACTION_CHANGE_USER_GROUPS);
        EventBus.getDefault().post(customEventBusMessage);
    }


}
