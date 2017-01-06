package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.adapter.MyGroupsAdapter;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.CustomEventBusMessage;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGroups extends EventBusFragment implements GetResponse {

    @BindView(R.id.gridViewGroups)
    GridView gridViewGroups;
    @BindView(R.id.swipeContainerMyGroup)
    SwipeRefreshLayout swipeContainerMyGroup;
    private UserGroupAction userGroupAction;
    private static final String ACTION_GET_USER_GROUPS_FROM_SERVER = "action_get_user_groups_from_server";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_groups, container, false);
        ButterKnife.bind(this, view);

        userGroupAction = new UserGroupAction(this);
        getGroupsFromLocalDatabase();
        getGroupsFromServer();
        swipeContainerMyGroup.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGroupsFromServer();
            }
        });
        return view;
    }

    private void getGroupsFromServer() {
        userGroupAction.getUserGroups(ACTION_GET_USER_GROUPS_FROM_SERVER);
    }

    private void getGroupsFromLocalDatabase() {
        ArrayList<UserGroups> userGroups = (ArrayList<UserGroups>) UserGroups.listAll(UserGroups.class);
        MyGroupsAdapter myGroupsAdapter = new MyGroupsAdapter(userGroups, getContext());
        gridViewGroups.setAdapter(myGroupsAdapter);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_GET_USER_GROUPS_FROM_SERVER)) {
            swipeContainerMyGroup.setRefreshing(false);
            getGroupsFromLocalDatabase();
        }
    }

    @Override
    public void getResponseFail(Object object, String action) {
        swipeContainerMyGroup.setRefreshing(false);
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        swipeContainerMyGroup.setRefreshing(false);
        Toast.makeText(getContext(), (Integer) object, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponseTokenExpired() {
        swipeContainerMyGroup.setRefreshing(false);
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(getContext());
    }

    @Subscribe
    public void onEvent(CustomEventBusMessage event) {
        if (event.getCustomMessage().equals(ACTION_REFRESH_USER_LIST) || event.getCustomMessage().equals(ACTION_CHANGE_USER_GROUPS)) {
            getGroupsFromLocalDatabase();
        }
    }

}
