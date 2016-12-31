package com.nextbest.skalkowski.whatwedo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserActions;
import com.nextbest.skalkowski.whatwedo.adapter.MenuAdapter;
import com.nextbest.skalkowski.whatwedo.fragments.AddEvent;
import com.nextbest.skalkowski.whatwedo.fragments.AddGroup;
import com.nextbest.skalkowski.whatwedo.fragments.AllEvent;
import com.nextbest.skalkowski.whatwedo.fragments.MyEvent;
import com.nextbest.skalkowski.whatwedo.fragments.MyGroups;
import com.nextbest.skalkowski.whatwedo.fragments.Settings;
import com.nextbest.skalkowski.whatwedo.fragments.UserPreferences;
import com.nextbest.skalkowski.whatwedo.fragments.UserProfile;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.nextbest.skalkowski.whatwedo.local_database.Token;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BasicActivity
        implements GetResponse {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.listViewMenu)
    ListView listViewMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private LoggedUser loggedUser;
    private UserActions userActions;
    private ProgressDialog progressDialogLoad;

    private static final String ACTION_LOGOUT = "action_logout";
    private static final String ACTION_GET_MENU_ITEM = "action_get_menu_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loggedUser = LoggedUser.getLoggedUser();
        initializeMenuUser();
        userActions = new UserActions(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeMenuUser() {
        if (loggedUser != null) {
            Picasso.with(this).load(loggedUser.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(profileImage);
            textViewName.setText(loggedUser.getName());
        }
        MenuAdapter menuAdapter = new MenuAdapter(com.nextbest.skalkowski.whatwedo.data_model.Menu.getMenuList(),
                this, this, ACTION_GET_MENU_ITEM);
        listViewMenu.setAdapter(menuAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getResponseSuccess(Object object, String action) {
        if (action.equals(ACTION_GET_MENU_ITEM)) {
            getMenuItem((Integer) object);
        } else if (action.equals(ACTION_LOGOUT)) {
            closeProgressDialogLoading();
            loggedUser.userLogout();
            openLoginActivity();
        }
    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        if (action.equals(ACTION_LOGOUT)) {
            closeProgressDialogLoading();
            Toast.makeText(this, (Integer) object, Toast.LENGTH_SHORT).show();
        }
    }

    private void closeProgressDialogLoading() {
        if (progressDialogLoad != null) {
            progressDialogLoad.dismiss();
        }
    }

    @Override
    public void getResponseTokenExpired() {

    }

    private void getMenuItem(int textId) {
        switch (textId) {
            case R.string.menuPreferences:
                openPreferences();
                break;
            case R.string.menuAddActivity:
                openAddEvent();
                break;
            case R.string.menuLogout:
                logout();
                break;
            case R.string.menuAllEvent:
                openAllEvent();
                break;
            case R.string.menuAddGroup:
                openAddGroup();
                break;
            case R.string.menuMyEvent:
                openMyEvent();
                break;
            case R.string.menuMyGroup:
                openMyGroups();
                break;
            case R.string.menuSettings:
                openSettings();
                break;
            case R.string.menuMyProfile:
                openUserProfile();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void openPreferences() {
        UserPreferences userPreferences = new UserPreferences();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, userPreferences).commit();
    }

    private void openAddEvent() {
        AddEvent addEvent = new AddEvent();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, addEvent).commit();
    }

    private void openAllEvent() {
        AllEvent allEvent = new AllEvent();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, allEvent).commit();
    }

    private void openAddGroup() {
        AddGroup addGroup = new AddGroup();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, addGroup).commit();
    }

    private void openMyEvent() {
        MyEvent myEvent = new MyEvent();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myEvent).commit();
    }

    private void openMyGroups() {
        MyGroups myGroups = new MyGroups();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myGroups).commit();
    }

    private void openSettings() {
        Settings settings = new Settings();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, settings).commit();
    }

    private void openUserProfile() {
        UserProfile userProfile = new UserProfile();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, userProfile).commit();
    }

    private void logout() {
        progressDialogLoad = LoadingPage.getLoadingPage(this);
        userActions.logout(ACTION_LOGOUT);
    }
}
