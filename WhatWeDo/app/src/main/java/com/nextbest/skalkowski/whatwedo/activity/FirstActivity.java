package com.nextbest.skalkowski.whatwedo.activity;

import android.os.Bundle;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Token;
import com.nextbest.skalkowski.whatwedo.model.LoadBefore;
import com.orm.SugarContext;

public class FirstActivity extends BasicActivity implements GetResponse {

    private static final String ACTION_LOGIN_TO_APP = "action_login_to_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SugarContext.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if (Token.isToken()) {
//            LoadBefore loadBefore = new LoadBefore(this);
//            loadBefore.loginToApp(ACTION_LOGIN_TO_APP);
            openMainActivity();
        } else {
            openLoginActivity();
        }
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        openMainActivity();
    }

    @Override
    public void getResponseFail(Object object, String action) {
        //todo maybe token expired
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        //todo app fail
    }

    @Override
    public void getResponseTokenExpired() {
        //todo token expired
    }
}
