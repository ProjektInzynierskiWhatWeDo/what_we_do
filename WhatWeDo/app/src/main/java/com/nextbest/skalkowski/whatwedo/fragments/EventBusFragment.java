package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;



public class EventBusFragment extends BasicFragment {

    public static final String ACTION_REFRESH_USER_LIST = "action_refresh_user_list";
    public static final String ACTION_CHANGE_USER_GROUPS = "action_change_user_groups";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
