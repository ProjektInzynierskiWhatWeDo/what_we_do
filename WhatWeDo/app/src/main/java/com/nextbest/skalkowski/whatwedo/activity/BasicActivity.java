package com.nextbest.skalkowski.whatwedo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;


public class BasicActivity extends AppCompatActivity {

    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";

    public void openLoginActivity() {
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        openLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openLoginActivity);
        this.finish();
    }

    public void openMainActivity() {
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainActivity);
        this.finish();
    }

    public void openMainPreferencesActivity() {
        //todo dodać jakiś parametr
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainActivity);
        this.finish();
    }

    public void openEditGroupActivity(UserGroups userGroups) {
        Intent openEditGroupActivity = new Intent(this, EditGroupActivity.class);
        openEditGroupActivity.putExtra(PUT_EXTRA_USER_GROUP,userGroups);
        startActivity(openEditGroupActivity);
    }

    public void openMemberManageActivity(UserGroups userGroups) {
        Intent openMemberManageActivity = new Intent(this, MemberManageActivity.class);
        openMemberManageActivity.putExtra(PUT_EXTRA_USER_GROUP,userGroups);
        startActivity(openMemberManageActivity);
    }

    public void openGroupView(){
        //todo dodać jakiś parametr
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainActivity);
        this.finish();
    }
}
