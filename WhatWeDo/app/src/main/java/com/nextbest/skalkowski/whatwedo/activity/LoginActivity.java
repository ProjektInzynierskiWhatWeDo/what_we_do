package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.UserLogin;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.model.LoadBefore;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BasicActivity implements GetResponse {

    @BindView(R.id.editTextEmail)
    @Email(messageResId = R.string.validateEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    @Password(min = 1, messageResId = R.string.validatePasswordLogin)
    EditText editTextPassword;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViewInvalidData)
    TextView textViewInvalidData;
    @BindView(R.id.editTextEmailLayout)
    TextInputLayout editTextEmailLayout;
    @BindView(R.id.editTextPasswordLayout)
    TextInputLayout editTextPasswordLayout;
    @BindView(R.id.textViewRegister)
    TextView textViewRegister;


    private UserActions userActions;
    private LoadBefore loadBefore;
    private Validator validator;
    private ProgressDialog progressDialog;


    private static final String ACTION_LOGIN_TO_APP = "login_to_app";
    private static final String ACTION_LOAD_DATA_BEFORE = "load_data_before";
    private boolean isFirstLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        userActions = new UserActions(this);
        loadBefore = new LoadBefore(this);
        validator = new Validator(this);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

    }


    public void validateFields() {
        clearView();
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                loginToApp();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(LoginActivity.this);

                    if (view instanceof EditText) {
                        if (view.equals(editTextEmail)) {
                            editTextEmailLayout.setErrorEnabled(true);
                            editTextEmailLayout.setError(message);
                        } else if (view.equals(editTextPassword)) {
                            editTextPasswordLayout.setErrorEnabled(true);
                            editTextPasswordLayout.setError(message);
                        }
                    }
                }
            }
        });
        validator.validate();
    }

    private void clearView() {
        editTextEmailLayout.setErrorEnabled(false);
        editTextPasswordLayout.setErrorEnabled(false);
        textViewInvalidData.setVisibility(View.GONE);
    }

    private void loginToApp() {
        createProgressDialog();
        UserLogin userLogin = new UserLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString(), null, null);
        userActions.loginToApp(userLogin, ACTION_LOGIN_TO_APP);
    }

    private void createProgressDialog() {
        progressDialog = LoadingPage.getLoadingPage(LoginActivity.this);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_LOGIN_TO_APP)) {
            closeProgressDialog();
            isFirstLogin = false;
            loadBeforeAppRun();
        } else if (action.equals(ACTION_LOAD_DATA_BEFORE)) {
            if (isFirstLogin) {
                openMainPreferencesActivity();
            } else {
                openMainActivity();
            }
        }
    }

    private void loadBeforeAppRun() {
        loadBefore.loginToApp(ACTION_LOAD_DATA_BEFORE);
        setContentView(R.layout.layout_load_data);
    }

    @Override
    public void getResponseFail(Object object, String action) {
        if (action.equals(ACTION_LOGIN_TO_APP)) {
            closeProgressDialog();
            textViewInvalidData.setVisibility(View.VISIBLE);
            textViewInvalidData.setText(object.toString());
        } else if (action.equals(ACTION_LOAD_DATA_BEFORE)) {
            Toast.makeText(this, R.string.loginError, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        if (action.equals(ACTION_LOGIN_TO_APP)) {
            closeProgressDialog();
            Toast.makeText(this, (Integer) object, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResponseTokenExpired() {

    }


    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
