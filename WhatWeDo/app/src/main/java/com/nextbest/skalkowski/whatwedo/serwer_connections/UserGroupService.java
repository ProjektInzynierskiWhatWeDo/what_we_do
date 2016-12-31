package com.nextbest.skalkowski.whatwedo.serwer_connections;


import com.nextbest.skalkowski.whatwedo.data_model.GetGroupMember;
import com.nextbest.skalkowski.whatwedo.data_model.GroupMemberResponse;
import com.nextbest.skalkowski.whatwedo.data_model.InviteUser;
import com.nextbest.skalkowski.whatwedo.data_model.ResponseToInvite;
import com.nextbest.skalkowski.whatwedo.data_model.StandardResponse;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroupsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserGroupService {
    @POST("sendInviteToUser")
    Call<StandardResponse> sendInviteToUser(@Body InviteUser inviteUser);

    @POST("responseToInvite")
    Call<StandardResponse> responseToInvite(@Body ResponseToInvite responseToInvite);

    @GET("getUserGroupsAndInviteToGroup")
    Call<UserGroupsResponse> getUsersGroupsAndInvite();

    @POST("getGroupMember")
    Call<GroupMemberResponse> getGroupMember(@Body GetGroupMember getGroupMember);
}
