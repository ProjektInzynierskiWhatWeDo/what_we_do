package com.nextbest.skalkowski.whatwedo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.GroupAction;
import com.nextbest.skalkowski.whatwedo.activity.LoginActivity;
import com.nextbest.skalkowski.whatwedo.data_model.Group;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddGroup extends BasicFragment implements Validator.ValidationListener, GetResponse {

    @Length(min = 5, messageResId = R.string.validateGroupName)
    @BindView(R.id.editTextGroupName)
    EditText editTextGroupName;
    @BindView(R.id.buttonAddGroup)
    Button buttonAddGroup;
    @BindView(R.id.textInputGroupName)
    TextInputLayout textInputGroupName;

    private Validator validator;
    private GroupAction groupAction;
    private static final String ACTION_ADD_GROUP = "action_add_group";
    private ProgressDialog progressDialogLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_group, container, false);
        ButterKnife.bind(this, view);
        groupAction = new GroupAction(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        return view;
    }

    private void validateFields() {
        clearValidation();
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        progressDialogLoad = LoadingPage.getLoadingPage(getContext());
        groupAction.addGroup(new Group(editTextGroupName.getText().toString()), ACTION_ADD_GROUP);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            if (view instanceof EditText) {
                textInputGroupName.setError(message);
                textInputGroupName.setErrorEnabled(true);
            }
        }
    }

    private void clearValidation() {
        textInputGroupName.setErrorEnabled(false);
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        closeProgressDialogLoad();
        Toast.makeText(getContext(),(Integer)object,Toast.LENGTH_SHORT).show();
        openMyGroups();
    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        closeProgressDialogLoad();
        Toast.makeText(getContext(),(Integer)object,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponseTokenExpired() {
        closeProgressDialogLoad();
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(getContext());
    }

    private void closeProgressDialogLoad() {
        if (progressDialogLoad != null) {
            progressDialogLoad.dismiss();
        }
    }
}
