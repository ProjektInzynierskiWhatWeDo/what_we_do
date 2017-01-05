package com.nextbest.skalkowski.whatwedo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.data_model.SearchUser;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberManageActivity extends BasicActivity implements GetResponse, TextWatcher {

    @BindView(R.id.autoCompleteTextViewSearchUser)
    AutoCompleteTextView autoCompleteTextViewSearchUser;
    @BindView(R.id.listViewMembers)
    ListView listViewMembers;

    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";
    private static final String ACTION_FIND_USER = "action_find_user";


    private UserActions userActions;
    private UserGroups userGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userGroups = (UserGroups) getIntent().getSerializableExtra(PUT_EXTRA_USER_GROUP);
        setContentView(R.layout.activity_member_manage);
        ButterKnife.bind(this);
        userActions = new UserActions(this);

    }

    @Override
    public void getResponseSuccess(Object object, String action) {
         if(action.equals(ACTION_FIND_USER)){
             
         }
    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {

    }

    @Override
    public void getResponseTokenExpired() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() >= 3) {
            searchUser(charSequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void searchUser(String name) {
        userActions.findUser(new SearchUser(userGroups.getGroup_id(),name),ACTION_FIND_USER);
    }
}
