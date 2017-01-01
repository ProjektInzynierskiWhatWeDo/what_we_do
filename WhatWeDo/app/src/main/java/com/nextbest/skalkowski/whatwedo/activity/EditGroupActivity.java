package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.GroupAction;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.data_model.DeleteGroup;
import com.nextbest.skalkowski.whatwedo.data_model.EditGroup;
import com.nextbest.skalkowski.whatwedo.data_model.Group;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditGroupActivity extends BasicActivity implements GetResponse, Validator.ValidationListener {

    @Length(min = 5, messageResId = R.string.validateGroupName)
    @BindView(R.id.editTextGroupName)
    EditText editTextGroupName;
    @BindView(R.id.textInputGroupName)
    TextInputLayout textInputGroupName;
    @BindView(R.id.buttonEditGroup)
    Button buttonEditGroup;
    @BindView(R.id.buttonDeleteGroup)
    Button buttonDeleteGroup;

    private GroupAction groupAction;
    private UserGroups userGroups;
    private Validator validator;
    private ProgressDialog progressDialogLoading;

    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";
    private static final String ACTION_DELETE_GROUP = "action_delete_group";
    private static final String ACTION_EDIT_GROUP = "action_edit_group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        userGroups = (UserGroups) getIntent().getSerializableExtra(PUT_EXTRA_USER_GROUP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        groupAction = new GroupAction(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        editTextGroupName.setText(userGroups.getName());
        buttonDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGroup();
            }
        });
        buttonEditGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearValidation();
                validator.validate();
            }
        });
    }

    private void createProgressDialogLading() {
        progressDialogLoading = LoadingPage.getLoadingPage(this);
    }

    private void closeProgressDialogLading() {
        if (progressDialogLoading != null) {
            progressDialogLoading.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void deleteGroup() {
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setMessage(R.string.textViewDeleteGroup)
                .setPositiveButton(R.string.deleteGroupPositiveButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createProgressDialogLading();
                        groupAction.deleteGroup(new DeleteGroup(userGroups.getGroup_id()), ACTION_DELETE_GROUP);
                    }
                })
                .setNegativeButton(R.string.deleteGroupNegativeButton, null)
                .show();
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        closeProgressDialogLading();
        if (action.equals(ACTION_DELETE_GROUP)) {
            showDeletedDialog();
        } else if (action.equals(ACTION_EDIT_GROUP)) {
              Toast.makeText(this,R.string.toastGroupNameChanged,Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeletedDialog() {
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setMessage(R.string.textViewDeleteGroupOk)
                .setPositiveButton(R.string.positiveButtonDeleteGroupOk, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGroupView();
                    }
                })
                .show();
    }

    @Override
    public void getResponseFail(Object object, String action) {
        closeProgressDialogLading();
        Toast.makeText(this, (String) object, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        closeProgressDialogLading();
        Toast.makeText(this, (Integer) object, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponseTokenExpired() {
        closeProgressDialogLading();
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(this);
    }

    private void clearValidation() {
        textInputGroupName.setErrorEnabled(false);
    }

    @Override
    public void onValidationSucceeded() {
        editGroup();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                textInputGroupName.setError(message);
                textInputGroupName.setErrorEnabled(true);
            }
        }
    }

    private void editGroup() {
        progressDialogLoading = LoadingPage.getLoadingPage(this);
        groupAction.editGroup(new EditGroup(userGroups.getGroup_id(),editTextGroupName.getText().toString()), ACTION_EDIT_GROUP);
    }
}
