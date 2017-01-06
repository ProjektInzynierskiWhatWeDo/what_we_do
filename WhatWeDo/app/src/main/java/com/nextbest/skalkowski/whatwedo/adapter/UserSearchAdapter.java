package com.nextbest.skalkowski.whatwedo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.UserGroupAction;
import com.nextbest.skalkowski.whatwedo.data_model.InviteUser;
import com.nextbest.skalkowski.whatwedo.data_model.UserFind;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Members;
import com.nextbest.skalkowski.whatwedo.local_database.UserGroups;
import com.nextbest.skalkowski.whatwedo.model.CustomEventBusMessage;
import com.nextbest.skalkowski.whatwedo.model.LoadingPage;
import com.nextbest.skalkowski.whatwedo.model.SessionExpired;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserSearchAdapter extends BaseAdapter implements GetResponse, Filterable {


    private Context context;
    private ArrayList<UserFind> userFinds;
    private int groupId;
    private UserGroupAction userGroupAction;
    private ProgressDialog progressDialogLoading;
    private Filter filter = new CustomFilter();
    private ArrayList<Object> suggestions = new ArrayList<>();
    private int positionId;
    private UserFind userFindSelected;

    private static final String ACTION_SEND_INVITE_TO_USER = "action_send_invite_to_user";
    private static final String ACTION_REFRESH_USER_LIST = "action_refresh_user_list";

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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                positionId = position;
                userFindSelected = userFind;
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

    private void sendInviteToUser(int userId) {
        userGroupAction.sendInviteToUser(new InviteUser(userId, groupId), ACTION_SEND_INVITE_TO_USER);
    }

    @Override
    public void getResponseSuccess(Object object, String action) {
        closeProgressDialog();
        Toast.makeText(context, (String) object, Toast.LENGTH_SHORT).show();
        userFinds.remove(positionId);
        new Members(userFindSelected.getId(), userFindSelected.getName(), userFindSelected.getEmail(), userFindSelected.getUser_image(),
                groupId, 0).save();
        UserGroups.changeMemberCount(groupId,userFindSelected.getUser_image(),userFindSelected.getName());
        CustomEventBusMessage customEventBusMessage = new CustomEventBusMessage();
        customEventBusMessage.setCustomMessage(ACTION_REFRESH_USER_LIST);
        EventBus.getDefault().post(customEventBusMessage);
    }

    @Override
    public void getResponseFail(Object object, String action) {
        closeProgressDialog();
        Toast.makeText(context, (String) object, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponseServerFail(Object object, String action) {
        closeProgressDialog();
        Toast.makeText(context, (Integer) object, Toast.LENGTH_SHORT).show();
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

    @Override
    public Filter getFilter() {
        return filter;
    }


    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();

            if (userFinds != null && constraint != null) {
                for (int i = 0; i < userFinds.size(); i++) {
                    suggestions.add(userFinds.get(i));
                }
            }
            FilterResults results = new FilterResults();
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
