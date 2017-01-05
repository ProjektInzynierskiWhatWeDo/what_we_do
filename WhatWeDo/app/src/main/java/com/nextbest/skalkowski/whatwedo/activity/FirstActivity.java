package com.nextbest.skalkowski.whatwedo.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Token;
import com.nextbest.skalkowski.whatwedo.model.LoadBefore;
import com.orm.SugarContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FirstActivity extends BasicActivity implements GetResponse {

    private static final String ACTION_LOGIN_TO_APP = "action_login_to_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SugarContext.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.nextbest.skalkowski.whatwedo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if (Token.isToken()) {
//            LoadBefore loadBefore = new LoadBefore(this);
//            loadBefore.loginToApp(ACTION_LOGIN_TO_APP);
            openMainActivity();
        } else {
            openSocialLoginActivity();
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
