package com.nextbest.skalkowski.whatwedo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.Member;
import com.nextbest.skalkowski.whatwedo.local_database.Members;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Members> members;

    public MemberListAdapter(Context context, ArrayList<Members> members) {
        this.context = context;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_member_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Members member = members.get(position);
        viewHolder.linearLayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.textViewUserName.setText(member.getName());
        Picasso.with(context).load(member.getUser_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(viewHolder.circleImageViewUser);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.circleImageViewUser)
        CircleImageView circleImageViewUser;
        @BindView(R.id.textViewUserName)
        TextView textViewUserName;
        @BindView(R.id.linearLayoutUser)
        LinearLayout linearLayoutUser;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
