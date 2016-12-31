package com.nextbest.skalkowski.whatwedo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class BasicActivity extends AppCompatActivity {
    public void openLoginActivity() {
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        openLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openLoginActivity);
        this.finish();
    }

    public void openMainActivity(){
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainActivity);
        this.finish();
    }

    public void openMainPreferencesActivity(){
        //todo dodać jakiś parametr
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMainActivity);
        this.finish();
    }
}
