package com.nextbest.skalkowski.whatwedo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;

import org.greenrobot.eventbus.EventBus;


public class BasicActivity extends AppCompatActivity {

    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";


    public void openLoginActivity() {
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(openLoginActivity);
    }

    public void openRegisterActivity() {
        Intent openRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(openRegisterActivity);
    }

    public void openSocialLoginActivity(){
        Intent openSocialLoginActivity = new Intent(this,SocialLoginActivity.class);
        openSocialLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openSocialLoginActivity);
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
