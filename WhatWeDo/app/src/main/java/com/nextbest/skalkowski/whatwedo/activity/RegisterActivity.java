package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.UserRegister;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.model.LoadBefore;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BasicActivity implements GetResponse {

    @BindView(R.id.editTextName)
    @Length(min = 5, messageResId = R.string.validateName)
    EditText editTextName;
    @BindView(R.id.editTextEmail)
    @Email(messageResId = R.string.validateEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    @Password(min = 5, messageResId = R.string.validatePasswordRegister)
    EditText editTextPassword;
    @BindView(R.id.editTextPasswordAgain)
    @ConfirmPassword(messageResId = R.string.validatePasswordAgain)
    EditText editTextPasswordAgain;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.editTextNameLayout)
    TextInputLayout editTextNameLayout;
    @BindView(R.id.editTextEmailLayout)
    TextInputLayout editTextEmailLayout;
    @BindView(R.id.editTextPasswordLayout)
    TextInputLayout editTextPasswordLayout;
    @BindView(R.id.editTextPasswordAgainLayout)
    TextInputLayout editTextPasswordAgainLayout;
    @BindView(R.id.textViewPrivacy)
    TextView textViewPrivacy;

    private UserActions userActions;
    private LoadBefore loadBefore;
    private Validator validator;
    private ProgressDialog progressDialog;

    private static final String ACTION_REGISTER_TO_APP = "register_to_app";
    private static final String ACTION_LOAD_DATA_BEFORE = "load_data_before";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        userActions = new UserActions(this);
        loadBefore = new LoadBefore(this);
        validator = new Validator(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    public void validateFields() {
        clearView();
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                registerToApp();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(RegisterActivity.this);

                    if (view instanceof EditText) {
                        if (view.equals(editTextEmail)) {
                            editTextEmailLayout.setErrorEnabled(true);
                            editTextEmailLayout.setError(message);
                        } else if (view.equals(editTextPassword)) {
                            editTextPasswordLayout.setErrorEnabled(true);
                            editTextPasswordLayout.setError(message);
                        } else if (view.equals(editTextName)) {
                            editTextNameLayout.setErrorEnabled(true);
                            editTextNameLayout.setError(message);
                        } else if (view.equals(editTextPasswordAgain)) {
                            editTextPasswordAgainLayout.setErrorEnabled(true);
                            editTextPasswordAgainLayout.setError(message);
                        }
                    }
                }
            }
        });
        validator.validate();
    }

    private void registerToApp() {
        progressDialog = LoadingPage.getLoadingPage(RegisterActivity.this);
        UserRegister userRegister = new UserRegister(editTextName.getText().toString(), editTextEmail.getText().toString(),
                editTextPassword.getText().toString());
        userActions.registerToApp(userRegister, ACTION_REGISTER_TO_APP);
    }

    private void clearView() {
        editTextEmailLayout.setErrorEnabled(false);
        editTextNameLayout.setErrorEnabled(false);
        editTextPasswordAgainLayout.setErrorEnabled(false);
        editTextPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_REGISTER_TO_APP)) {
            closeProgressDialog();
            loadBefore.loginToApp(ACTION_LOAD_DATA_BEFORE);
        } else if (action.equals(ACTION_LOAD_DATA_BEFORE)) {
            openMainPreferencesActivity();
        }
    }

    @Override
    public void getResponseFail(Object object, String action) {
        if (action.equals(ACTION_REGISTER_TO_APP)) {
            closeProgressDialog();
            editTextEmailLayout.setErrorEnabled(true);
            editTextEmailLayout.setError(object.toString());
        }else if(action.equals(ACTION_LOAD_DATA_BEFORE)){
            Toast.makeText(this,R.string.loginError,Toast.LENGTH_LONG).show();
            openLoginActivity();
        }
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
            closeProgressDialog();
            Toast.makeText(this, (Integer) object, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getResponseTokenExpired() {
        closeProgressDialog();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}