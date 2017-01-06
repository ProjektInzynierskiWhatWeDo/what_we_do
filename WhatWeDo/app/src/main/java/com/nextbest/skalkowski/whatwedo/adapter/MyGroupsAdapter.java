package com.nextbest.skalkowski.whatwedo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.activity.GroupActivity;
import com.nextbest.skalkowski.whatwedo.data_model.UserGroup;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.squareup.picasso.Picasso;

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
        viewHolder.textViewGroupName.setText(userGroup.getName());
        viewHolder.textViewCountGroupUser.setText(userGroup.getCount()+"");
        initializeImage(viewHolder,userGroup);


        return convertView;
    }

    private void initializeImage(ViewHolder viewHolder , UserGroups userGroup){
        switch (userGroup.getCount()){
            case 1:{
                hideAllImages(viewHolder);
                viewHolder.imageViewOne.setVisibility(View.VISIBLE);
                Picasso.with(context).load(userGroup.getFirst_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewOne);
                break;
            }
            case 2:{
                hideAllImages(viewHolder);
                viewHolder.imageViewTwoRight.setVisibility(View.VISIBLE);
                viewHolder.imageViewTwoLeft.setVisibility(View.VISIBLE);
                Picasso.with(context).load(userGroup.getFirst_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewTwoLeft);
                Picasso.with(context).load(userGroup.getSecond_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewTwoRight);
                break;
            }
            case 3:{
                hideAllImages(viewHolder);
                viewHolder.imageViewFourRightTop.setVisibility(View.VISIBLE);
                viewHolder.imageViewFourRightBottom.setVisibility(View.VISIBLE);
                viewHolder.imageViewTwoLeft.setVisibility(View.VISIBLE);
                Picasso.with(context).load(userGroup.getFirst_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewTwoLeft);
                Picasso.with(context).load(userGroup.getSecond_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourRightTop);
                Picasso.with(context).load(userGroup.getThird_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourRightBottom);

                break;
            }
            case 4:{
                hideAllImages(viewHolder);
                viewHolder.imageViewFourRightTop.setVisibility(View.VISIBLE);
                viewHolder.imageViewFourRightBottom.setVisibility(View.VISIBLE);
                viewHolder.imageViewFourLeftTop.setVisibility(View.VISIBLE);
                viewHolder.imageViewFourLeftBottom.setVisibility(View.VISIBLE);
                Picasso.with(context).load(userGroup.getFirst_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourLeftBottom);
                Picasso.with(context).load(userGroup.getSecond_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourRightTop);
                Picasso.with(context).load(userGroup.getThird_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourRightBottom);
                Picasso.with(context).load(userGroup.getFourth_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewFourLeftTop);

                break;
            }
            default:{
                viewHolder.imageViewOne.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                        .into(viewHolder.imageViewOne);
                hideAllImages(viewHolder);
                break;
            }
        }

    }

    private void hideAllImages(ViewHolder viewHolder){
        viewHolder.imageViewOne.setVisibility(View.GONE);
        viewHolder.imageViewTwoLeft.setVisibility(View.GONE);
        viewHolder.imageViewTwoRight.setVisibility(View.GONE);
        viewHolder.imageViewFourLeftBottom.setVisibility(View.GONE);
        viewHolder.imageViewFourLeftTop.setVisibility(View.GONE);
        viewHolder.imageViewFourRightBottom.setVisibility(View.GONE);
        viewHolder.imageViewFourRightTop.setVisibility(View.GONE);
    }



    private void openGroupView(UserGroups userGroups) {
        Intent openGroupView = new Intent(context, GroupActivity.class);
        openGroupView.putExtra(PUT_EXTRA_USER_GROUP, userGroups);
        context.startActivity(openGroupView);
    }

    class ViewHolder {
        @BindView(R.id.imageViewOne)
        ImageView imageViewOne;
        @BindView(R.id.imageViewTwoLeft)
        ImageView imageViewTwoLeft;
        @BindView(R.id.imageViewTwoRight)
        ImageView imageViewTwoRight;
        @BindView(R.id.imageViewFourLeftTop)
        ImageView imageViewFourLeftTop;
        @BindView(R.id.imageViewFourLeftBottom)
        ImageView imageViewFourLeftBottom;
        @BindView(R.id.imageViewFourRightTop)
        ImageView imageViewFourRightTop;
        @BindView(R.id.imageViewFourRightBottom)
        ImageView imageViewFourRightBottom;
        @BindView(R.id.imageViewCircle)
        ImageView imageViewCircle;
        @BindView(R.id.textViewGroupName)
        TextView textViewGroupName;
        @BindView(R.id.textViewCountGroupUser)
        TextView textViewCountGroupUser;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
