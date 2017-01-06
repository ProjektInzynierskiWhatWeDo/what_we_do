package com.nextbest.skalkowski.whatwedo.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.adapter.MemberListAdapter;
import com.nextbest.skalkowski.whatwedo.adapter.UserSearchAdapter;
import com.nextbest.skalkowski.whatwedo.data_model.SearchUser;
import com.nextbest.skalkowski.whatwedo.data_model.UserFind;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Members;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.CustomEventBusMessage;
import com.orm.util.NamingHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberManageActivity extends EventBusActivity implements GetResponse, TextWatcher {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        userActions = new UserActions(this);
        autoCompleteTextViewSearchUser.addTextChangedListener(this);
        loadMembersFromLocalDatabase();

    }
    private void loadMembersFromLocalDatabase() {
        ArrayList<Members> members = (ArrayList<Members>) Members.find(Members.class, NamingHelper.toSQLNameDefault("group_id") + " = ?", String.valueOf(userGroups.getGroup_id()));
        MemberListAdapter memberListAdapter = new MemberListAdapter(this, members);
        listViewMembers.setAdapter(memberListAdapter);
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_FIND_USER)) {
            ArrayList<UserFind> userFinds = (ArrayList<UserFind>) object;
            UserSearchAdapter userSearchAdapter = new UserSearchAdapter(this, userFinds, userGroups.getGroup_id());
            autoCompleteTextViewSearchUser.setAdapter(userSearchAdapter);
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

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() >= 3) {
            searchUser(editable.toString());
        }
    }

    private void searchUser(String name) {
        userActions.findUser(new SearchUser(userGroups.getGroup_id(), name), ACTION_FIND_USER);
    }


    @Subscribe
    public void onEvent(CustomEventBusMessage event) {
        if (event.getCustomMessage().equals(ACTION_REFRESH_USER_LIST)) {
            loadMembersFromLocalDatabase();
        }
    }
}
