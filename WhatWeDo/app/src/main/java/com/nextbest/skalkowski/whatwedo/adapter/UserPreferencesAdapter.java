package com.nextbest.skalkowski.whatwedo.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.actions.PreferenceAction;
import com.nextbest.skalkowski.whatwedo.data_model.SetUserPreference;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;
import com.nextbest.skalkowski.whatwedo.local_database.Preferences;
import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserPreferencesAdapter extends BaseAdapter implements GetResponse {

    private Context context;
    private ArrayList<UserPreference> userPreferences;
    private PreferenceAction preferenceAction;
    private GetResponse getResponseActivity;

    private static final String ACTION_SET_USER_PREFERENCE = "action_set_user_preference";

    public UserPreferencesAdapter(Context context, ArrayList<UserPreference> userPreferences, GetResponse getResponseActivity) {
        this.context = context;
        this.userPreferences = userPreferences;
        this.getResponseActivity = getResponseActivity;
        preferenceAction = new PreferenceAction(this);
    }

    @Override
    public int getCount() {
        return userPreferences.size();
    }

    @Override
    public Object getItem(int position) {
        return userPreferences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item_user_preferences, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final UserPreference userPreference = (UserPreference) getItem(position);
        viewHolder.linearLayoutContainer.setBackgroundResource(Preferences.getPreferenceColor(userPreference.getPreferenceId()));
        viewHolder.textViewName.setText(userPreference.getName());
        viewHolder.switchStatus.setOnCheckedChangeListener(null);
        viewHolder.switchStatus.setChecked(userPreference.getStatus());
        viewHolder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = viewHolder.switchStatus.isChecked();
                sendPreferenceToServer(userPreference, status);
            }
        });

        return convertView;
    }

    private void sendPreferenceToServer(UserPreference userPreference, boolean status) {
        userPreference.setStatus(status);
        userPreference.save();
        int preferenceStatus = (status) ? 1 : 0;
        SetUserPreference setUserPreference = new SetUserPreference(userPreference.getPreferenceId(), preferenceStatus);
        preferenceAction.setUserPreference(ACTION_SET_USER_PREFERENCE, setUserPreference);
        getResponseActivity.getResponseSuccess(ACTION_SET_USER_PREFERENCE, ACTION_SET_USER_PREFERENCE);
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

    }


    class ViewHolder {
        @BindView(R.id.imageViewPicture)
        ImageView imageViewPicture;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.switchStatus)
        SwitchCompat switchStatus;
        @BindView(R.id.linearLayoutContainer)
        LinearLayout linearLayoutContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
