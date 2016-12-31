package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.GroupAction;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.data_model.DeleteGroup;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        userGroups = (UserGroups) getIntent().getSerializableExtra(PUT_EXTRA_USER_GROUP);
        ButterKnife.bind(this);
        groupAction = new GroupAction(this);
        validator = new Validator(this);


        buttonDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGroup();
            }
        });

        buttonEditGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


    private void deleteGroup() {
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorAccent)
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

        }
    }

    @Override
    public void getResponseFail(Object object, String action) {
        createProgressDialogLading();
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        createProgressDialogLading();
    }

    @Override
    public void getResponseTokenExpired() {
        createProgressDialogLading();
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(this);
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

    private void editGroup() {
        //groupAction.editGroup(new Group());
    }
}
