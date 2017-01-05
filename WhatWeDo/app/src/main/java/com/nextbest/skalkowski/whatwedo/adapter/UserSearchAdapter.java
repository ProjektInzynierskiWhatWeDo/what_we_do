package com.nextbest.skalkowski.whatwedo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.data_model.InviteUser;
import com.nextbest.skalkowski.whatwedo.data_model.UserFind;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserSearchAdapter extends BaseAdapter implements GetResponse{


    private Context context;
    private ArrayList<UserFind> userFinds;
    private int groupId;
    private UserGroupAction userGroupAction;
    private ProgressDialog progressDialogLoading;

    private static final String ACTION_SEND_INVITE_TO_USER = "action_send_invite_to_user";

    public UserSearchAdapter(Context context, ArrayList<UserFind> userFinds, int groupId) {
        this.context = context;
        this.userFinds = userFinds;
        this.groupId = groupId;
        userGroupAction = new UserGroupAction(this);
    }

    @Override
    public int getCount() {
        return userFinds.size();
    }

    @Override
    public Object getItem(int position) {
        return userFinds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_search_user, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserFind userFind = userFinds.get(position);
        Picasso.with(context).load(userFind.getUser_image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(viewHolder.circleImageViewUser);
        viewHolder.textViewUserName.setText(userFind.getName());
        viewHolder.buttonSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProgressDialog();
                sendInviteToUser(userFind.getId());
            }
        });

        return convertView;
    }



    static class ViewHolder {
        @BindView(R.id.circleImageViewUser)
        CircleImageView circleImageViewUser;
        @BindView(R.id.textViewUserName)
        TextView textViewUserName;
        @BindView(R.id.buttonSendInvite)
        Button buttonSendInvite;
        @BindView(R.id.linearLayoutContainer)
        LinearLayout linearLayoutContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void sendInviteToUser(int userId){
        userGroupAction.sendInviteToUser(new InviteUser(userId,groupId),ACTION_SEND_INVITE_TO_USER);
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        closeProgressDialog();
       if(action.equals(ACTION_SEND_INVITE_TO_USER)){
           //todo
       }
    }

    @Override
    public void getResponseFail(Object object, String action) {
        closeProgressDialog();
        //todo
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        closeProgressDialog();
        //todo
    }

    @Override
    public void getResponseTokenExpired() {
        closeProgressDialog();
        SessionExpired sessionExpired = new SessionExpired();
        sessionExpired.sessionExpired(context);
    }

    private void createProgressDialog() {
        progressDialogLoading = LoadingPage.getLoadingPage(context);
    }

    private void closeProgressDialog() {
        if (progressDialogLoading != null) {
            progressDialogLoading.dismiss();
        }
    }
}
