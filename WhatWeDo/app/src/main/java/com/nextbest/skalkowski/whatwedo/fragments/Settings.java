package com.nextbest.skalkowski.whatwedo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.local_database.UserSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Settings extends Fragment {

    @BindView(R.id.textViewDistance)
    TextView textViewDistance;
    @BindView(R.id.progressBarDistance)
    ProgressBar progressBarDistance;
    @BindView(R.id.switchNotification)
    SwitchCompat switchNotification;
    @BindView(R.id.switchVibration)
    SwitchCompat switchVibration;
    @BindView(R.id.switchSound)
    SwitchCompat switchSound;

    private UserSettings userSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        userSettings = UserSettings.getUserSettings();
        initializeUserSettings();
        return view;
    }

    private void initializeUserSettings(){
        switchNotification.setChecked(userSettings.isNotification());
        switchSound.setChecked(userSettings.isSound());
        switchVibration.setChecked(userSettings.isVibrate());
        textViewDistance.setText(userSettings.getDistance()+"");
        progressBarDistance.setProgress(userSettings.getDistance());
    }
    //todo zrobić reszte


}
