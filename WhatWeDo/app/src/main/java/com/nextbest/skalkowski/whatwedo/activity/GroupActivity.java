package com.nextbest.skalkowski.whatwedo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.fragments.ChatFragment;
import com.nextbest.skalkowski.whatwedo.fragments.GroupMemberFragment;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.LoggedUser;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupActivity extends BasicActivity implements ViewPager.OnPageChangeListener , GetResponse{

    @BindView(R.id.tabGroup)
    TabLayout tabGroup;
    @BindView(R.id.viewPagerGroup)
    ViewPager viewPagerGroup;
    @BindView(R.id.activity_group)
    RelativeLayout activityGroup;

    private UserGroups userGroups;

    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";
    private static final String PUT_EXTRA_ACTION = "put_extra_action";
    private static final String ACTION_GROUP_CHAT = "put_extra_group_chat";
    private static final String PUT_EXTRA_ACTION_ID = "put_extra_action_id";
    private static final String PUT_EXTRA_OWNER_ID = "put_extra_owner_id";

    private UserGroupAction userGroupAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        userGroups = (UserGroups) getIntent().getSerializableExtra(PUT_EXTRA_USER_GROUP);
        userGroupAction = new UserGroupAction(this);
        getSupportActionBar().setTitle(userGroups.getName());
        initializeViewPager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(UserGroups.getGroupName(userGroups.getGroup_id()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_edit_group:
                openEditGroupActivity(userGroups);
                break;
            case R.id.action_manage_users:
                openMemberManageActivity(userGroups);
                break;
            case R.id.action_leave_group:
                userGroupAction.
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        LoggedUser user = LoggedUser.getLoggedUser();
        if (user.getIdUser() == userGroups.getOwner_id()) {
            inflater.inflate(R.menu.menu_group_owner, menu);
        } else {
            inflater.inflate(R.menu.menu_group_member, menu);
        }
        return true;
    }


    public void initializeViewPager() {
        Bundle bundleChat = new Bundle();
        bundleChat.putString(PUT_EXTRA_ACTION, ACTION_GROUP_CHAT);
        bundleChat.putInt(PUT_EXTRA_ACTION_ID, userGroups.getGroup_id());
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundleChat);

        Bundle bundleMember = new Bundle();
        bundleMember.putInt(PUT_EXTRA_ACTION_ID, userGroups.getGroup_id());
        bundleMember.putInt(PUT_EXTRA_OWNER_ID, userGroups.getOwner_id());
        GroupMemberFragment groupMemberFragment = new GroupMemberFragment();
        groupMemberFragment.setArguments(bundleMember);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(groupMemberFragment, getString(R.string.tabGroupMember));
        adapter.addFragment(chatFragment, getString(R.string.tabChat));
        viewPagerGroup.setOffscreenPageLimit(2);
        viewPagerGroup.addOnPageChangeListener(this);
        viewPagerGroup.setAdapter(adapter);
        tabGroup.setupWithViewPager(viewPagerGroup);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void getResponseSuccess(Object object, String action) {

    }

    @Override
    public void getResponseFail(Object object, String action) {

    }

    @Override
    public void getResponseServerFail(Object object, String action) {

    }

    @Override
    public void getResponseTokenExpired() {
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(this);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


}
