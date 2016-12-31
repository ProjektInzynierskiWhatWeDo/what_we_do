package com.nextbest.skalkowski.whatwedo.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.nextbest.skalkowski.whatwedo.activity.LoginActivity;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class SessionExpired {
    public  void sessionExpired(final Context context){
         new LovelyStandardDialog(context)
                 .setPositiveButton("ok", new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         LoggedUser loggedUser = new LoggedUser();
                         loggedUser.userLogout();
                         openLoginActivity(context);
                     }
                 })
                 .show();
    }

    private  void openLoginActivity(Context context) {
        Intent openLoginActivity = new Intent(context, LoginActivity.class);
        openLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(openLoginActivity);
    }
}
