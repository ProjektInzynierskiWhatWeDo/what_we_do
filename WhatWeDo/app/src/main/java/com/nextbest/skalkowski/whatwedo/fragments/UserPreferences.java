package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.adapter.UserPreferencesAdapter;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;
import com.orm.util.NamingHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPreferences extends Fragment implements GetResponse {

    @BindView(R.id.listViewPreferences)
    ListView listViewPreferences;
    @BindView(R.id.textViewCount)
    TextView textViewCount;

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
        UserPreferencesAdapter userPreferencesAdapter = new UserPreferencesAdapter(getContext(), userPreferences , this);
        listViewPreferences.setAdapter(userPreferencesAdapter);
        countPreferences();
    }

    private void countPreferences() {
        long selectedCount = UserPreference.count(UserPreference.class, "status = ?", new String[]{String.valueOf(1)});
        textViewCount.setText(getString(R.string.textViewPreferencesCount) + " " + selectedCount);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        countPreferences();
    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {

    }

    @Override
    public void getResponseTokenExpired() {

    }
}
