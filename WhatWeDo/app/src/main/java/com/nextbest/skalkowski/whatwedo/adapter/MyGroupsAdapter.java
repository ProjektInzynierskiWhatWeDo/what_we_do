package com.nextbest.skalkowski.whatwedo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.activity.GroupActivity;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyGroupsAdapter extends BaseAdapter {

    private ArrayList<UserGroups> userGroups;
    private Context context;
    private static final String PUT_EXTRA_USER_GROUP = "put_extra_user_group";

    public MyGroupsAdapter(ArrayList<UserGroups> userGroups, Context context) {
        this.userGroups = userGroups;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return userGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_my_group_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserGroups userGroup = userGroups.get(position);
        viewHolder.linearLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroupView(userGroup);
            }
        });
        viewHolder.textViewName.setText(userGroup.getName());


        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void openGroupView(UserGroups userGroups) {
        Intent openGroupView = new Intent(context, GroupActivity.class);
        openGroupView.putExtra(PUT_EXTRA_USER_GROUP, userGroups);
        context.startActivity(openGroupView);
    }
}
