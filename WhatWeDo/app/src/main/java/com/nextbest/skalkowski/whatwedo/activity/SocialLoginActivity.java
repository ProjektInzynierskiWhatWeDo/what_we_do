package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogin;
import com.nextbest.skalkowski.whatwedo.data_model.UserLoginResponse;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.model.LoadBefore;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;

import java.io.IOException;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SocialLoginActivity extends BasicActivity implements GetResponse, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.buttonFacebook)
    Button buttonFacebook;
    @BindView(R.id.buttonGoogle)
    Button buttonGoogle;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViewRegister)
    TextView textViewRegister;
    @BindView(R.id.facebookLoginButton)
    LoginButton facebookLoginButton;

    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private UserActions userActions;
    private LoadBefore loadBefore;


    private static final String ACTION_LOGIN_SOCIAL_TO_APP = "login_social_to_app";
    private static final String ACTION_LOAD_DATA_BEFORE = "load_data_before";
    private static final int GOOGLE_SIGN_IN = 0;
    private static final int FACEBOOK_SIGN_IN = 64206;
    private boolean isFirstLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);
        ButterKnife.bind(this);

        initializeSocialLogin();
        initialize();
    }


    private void initializeSocialLogin() {
        userActions = new UserActions(this);
        loadBefore = new LoadBefore(this);
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleClientId))
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        facebookLoginButton.setReadPermissions(Collections.singletonList("public_profile, email, user_photos"));
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        facebookLogin(loginResult.getAccessToken().getToken());
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), getString(R.string.googleClientId), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.d(getClass().getName(), "onError: " + e.getCause().toString());
                        if (e.getMessage().contains("FAILURE")) {
                            Toast.makeText(getApplicationContext(), getString(R.string.googleClientId), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Facebook on error +" + e.getMessage(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(),getString(R.string.cantLoginByFacebook),Toast.LENGTH_LONG).show();
                        }

                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void initialize() {
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLoginButton.performClick();
            }
        });

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    private void createProgressDialog() {
        progressDialog = LoadingPage.getLoadingPage(SocialLoginActivity.this);
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_LOGIN_SOCIAL_TO_APP)) {
            closeProgressDialog();
            isFirstLogin = ((UserLoginResponse) object).getRegister();
            loadBeforeAppRun();
        }else if (action.equals(ACTION_LOAD_DATA_BEFORE)) {
            if (isFirstLogin) {
                openMainPreferencesActivity();
            } else {
                openMainActivity();
            }
        }
    }

    private void loadBeforeAppRun(){
        loadBefore.loginToApp(ACTION_LOAD_DATA_BEFORE);
        setContentView(R.layout.layout_load_data);
    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        if (action.equals(ACTION_LOGIN_SOCIAL_TO_APP)) {
            closeProgressDialog();
            Toast.makeText(this, (Integer) object, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResponseTokenExpired() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
            new RetrieveTokenTask().execute(googleSignInAccount.getEmail());
        } else {
            Toast.makeText(getApplicationContext(), R.string.googleLoginError, Toast.LENGTH_LONG).show();
        }
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException | GoogleAuthException e) {
                Toast.makeText(getApplicationContext(), R.string.googleLoginError, Toast.LENGTH_LONG).show();
            }
            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);
            googleLogin(token);
        }
    }

    private void googleLogin(String googleToken) {
        Log.d("QWERTY", "googleLogin: " + googleToken);
        createProgressDialog();
        UserLogin userLogin = new UserLogin(null, null, googleToken, null);
        userActions.socialLoginToApp(userLogin, ACTION_LOGIN_SOCIAL_TO_APP);
    }

    private void facebookLogin(String facebookToken) {
        Log.d("QWERTY", "facebookLogin: " + facebookToken);
        createProgressDialog();
        UserLogin userLogin = new UserLogin(null, null, null, facebookToken);
        userActions.socialLoginToApp(userLogin, ACTION_LOGIN_SOCIAL_TO_APP);
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == FACEBOOK_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}

