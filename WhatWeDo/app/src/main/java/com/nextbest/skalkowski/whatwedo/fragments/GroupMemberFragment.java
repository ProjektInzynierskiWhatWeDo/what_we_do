package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.adapter.MemberListAdapter;
import com.nextbest.skalkowski.whatwedo.data_model.GetGroupMember;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.data_model.GroupMemberResponse;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.nextbest.skalkowski.whatwedo.data_model.Member;
import com.nextbest.skalkowski.whatwedo.local_database.Members;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroupMemberFragment extends BasicFragment implements GetResponse {


    @BindView(R.id.listViewMembers)
    ListView listViewMembers;
    @BindView(R.id.buttonMembers)
    Button buttonMembers;

    private static final String PUT_EXTRA_ACTION_ID = "put_extra_action_id";
    private static final String PUT_EXTRA_OWNER_ID = "put_extra_owner_id";
    private static final String ACTION_GET_GROUP_MEMBER = "action_get_group_member";

    private UserGroupAction userGroupAction;
    private int groupId;
    private int ownerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member, container, false);
        groupId = getArguments().getInt(PUT_EXTRA_ACTION_ID, 0);
        ownerId = getArguments().getInt(PUT_EXTRA_OWNER_ID, 0);
        ButterKnife.bind(this, view);
        userGroupAction = new UserGroupAction(this);
        checkOwner();
        loadMembersFromLocalDatabase();
        loadMembersFromServer();

        return view;
    }

    private void checkOwner() {
        if (ownerId == LoggedUser.getLoggedUser().getId()) {
            buttonMembers.setVisibility(View.VISIBLE);
            buttonMembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void loadMembersFromLocalDatabase() {
        ArrayList<Members> members = (ArrayList<Members>) Members.find(Members.class, NamingHelper.toSQLNameDefault("group_id") + " = ?", String.valueOf(groupId));
        MemberListAdapter memberListAdapter = new MemberListAdapter(getContext(), members);
        listViewMembers.setAdapter(memberListAdapter);
    }

    private void loadMembersFromServer() {
        userGroupAction.getGroupMember(new GetGroupMember(groupId), ACTION_GET_GROUP_MEMBER);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_GET_GROUP_MEMBER)) {
            loadMembersFromLocalDatabase();
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
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(getContext());
    }
}