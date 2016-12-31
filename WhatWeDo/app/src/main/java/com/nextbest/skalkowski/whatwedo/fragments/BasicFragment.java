package com.nextbest.skalkowski.whatwedo.fragments;

import android.support.v4.app.Fragment;

import com.nextbest.skalkowski.whatwedo.R;

public class BasicFragment extends Fragment {

    public void openMyGroups() {
        MyGroups myGroups = new MyGroups();
        getFragmentManager().beginTransaction().replace(R.id.content_main, myGroups).commit();
    }
}
