package com.nextbest.skalkowski.whatwedo.actions;


import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.GetGroupMember;
import com.nextbest.skalkowski.whatwedo.data_model.GroupMemberResponse;
import com.nextbest.skalkowski.whatwedo.data_model.InviteUser;
import com.nextbest.skalkowski.whatwedo.data_model.ResponseToInvite;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroupsResponse;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.GroupMemberImage;
import com.nextbest.skalkowski.whatwedo.data_model.Member;
import com.nextbest.skalkowski.whatwedo.local_database.Members;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.serwer_connections.ServiceGenerator;
import com.nextbest.skalkowski.whatwedo.serwer_connections.UserGroupService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGroupAction extends Action {
    private GetResponse getResponse;

    public UserGroupAction(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    public void sendInviteToUser(InviteUser inviteUser, final String action) {
        UserGroupService userGroupService = ServiceGenerator.createServiceToken(UserGroupService.class);
        Call<StandardResponse> call = userGroupService.sendInviteToUser(inviteUser);
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

    public void responseToInvite(ResponseToInvite responseToInvite, final String action) {
        UserGroupService userGroupService = ServiceGenerator.createServiceToken(UserGroupService.class);
        Call<StandardResponse> call = userGroupService.responseToInvite(responseToInvite);
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

    public void getUserGroupsAndInvite(final String action) {
        final UserGroupService userGroupService = ServiceGenerator.createServiceToken(UserGroupService.class);
        Call<UserGroupsResponse> userGroupsResponseCall = userGroupService.getUsersGroupsAndInvite();
        userGroupsResponseCall.enqueue(new Callback<UserGroupsResponse>() {
            @Override
            public void onResponse(Call<UserGroupsResponse> call, Response<UserGroupsResponse> response) {
                if (response.isSuccessful()) {
                    UserGroupsResponse userGroupsResponse = response.body();
                    List<UserGroup> userGroups = userGroupsResponse.getMessage();
                    UserGroups.deleteAll(UserGroups.class);
                    for (UserGroup userGroup : userGroups) {
                        String first_image = null;
                        String second_image = null;
                        String third_image = null;
                        String fourth_image = null;
                        int i = 0;
                        for (GroupMemberImage groupMemberImage : userGroup.getMember_image()) {
                            if (i == 0) {
                                first_image = userGroup.getMember_image().get(0).getUser_image();
                            } else if (i == 1) {
                                second_image = userGroup.getMember_image().get(1).getUser_image();
                            } else if (i == 2) {
                                third_image = userGroup.getMember_image().get(2).getUser_image();
                            } else if (i == 3) {
                                fourth_image = userGroup.getMember_image().get(3).getUser_image();
                            }
                            i++;
                        }
                        new UserGroups(userGroup.getGroup_id(), userGroup.getOwner_id(), userGroup.getName(),
                                userGroup.getStatus(), userGroup.getCount(), first_image, second_image, third_image, fourth_image).save();
                    }
                    getResponse.getResponseSuccess(action, action);
                } else {
                    if (response.code() == HTTP_RESPONSE_UNAUTHORIZED) {
                        getResponse.getResponseTokenExpired();
                    } else {
                        getResponse.getResponseServerFail(R.string.serverError, action);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserGroupsResponse> call, Throwable t) {
                Log.d(getClass().getName(), "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

    public void getGroupMember(GetGroupMember getGroupMember, final String action) {
        UserGroupService userGroupService = ServiceGenerator.createServiceToken(UserGroupService.class);
        Call<GroupMemberResponse> groupMemberResponseCall = userGroupService.getGroupMember(getGroupMember);
        groupMemberResponseCall.enqueue(new Callback<GroupMemberResponse>() {
            @Override
            public void onResponse(Call<GroupMemberResponse> call, Response<GroupMemberResponse> response) {
                if (response.isSuccessful()) {
                    GroupMemberResponse groupMemberResponse = response.body();
                    if (groupMemberResponse.getStatus() == STATUS_SUCCESS) {
                        List<Member> members = groupMemberResponse.getMessage();
                        Members.deleteByGroupId(groupMemberResponse.getGroup_id());
                        for (Member member : members) {
                            new Members(member.getUser_id(), member.getName(),
                                    member.getEmail(), member.getUser_image(), groupMemberResponse.getGroup_id(), 0).save();
                        }
                        getResponse.getResponseSuccess(action, action);
                    } else {
                        //todo or not check group exist
                        getResponse.getResponseFail(groupMemberResponse.getGroup_id(), action);
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
            public void onFailure(Call<GroupMemberResponse> call, Throwable t) {
                Log.d(getClass().getName(), "onFailure: " + t.getMessage());
                getResponse.getResponseServerFail(R.string.connectionError, action);
            }
        });
    }

}
