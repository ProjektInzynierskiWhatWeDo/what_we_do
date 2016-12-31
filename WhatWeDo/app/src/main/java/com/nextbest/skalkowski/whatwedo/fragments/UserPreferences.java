package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.adapter.UserPreferencesAdapter;
import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPreferences extends Fragment {

    @BindView(R.id.listViewPreferences)
    ListView listViewPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userPreferencesView = inflater.inflate(R.layout.fragment_user_preferences, container, false);
        ButterKnife.bind(this, userPreferencesView);

        getDataToListViewPreferences();

        return userPreferencesView;
    }

    private void getDataToListViewPreferences() {
        ArrayList<UserPreference> userPreferences = (ArrayList<UserPreference>) UserPreference.listAll(UserPreference.class);
        UserPreferencesAdapter userPreferencesAdapter = new UserPreferencesAdapter(getContext(),userPreferences);
        listViewPreferences.setAdapter(userPreferencesAdapter);
    }


}
