package com.nextbest.skalkowski.whatwedo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.data_model.Menu;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MenuAdapter extends BaseAdapter {

    private ArrayList<Menu> menus;
    private Context context;
    private GetResponse getResponse;
    private String action;

    public MenuAdapter(ArrayList<Menu> menus, Context context, GetResponse getResponse, String action) {
        this.menus = menus;
        this.context = context;
        this.getResponse = getResponse;
        this.action = action;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_menu, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Menu menu = menus.get(position);
        viewHolder.textViewName.setText(context.getString(menu.getTextId()));
        viewHolder.linearLayoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuItem(menu.getTextId());
            }
        });
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.imageViewIcon)
        ImageView imageViewIcon;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.linearLayoutMenu)
        LinearLayout linearLayoutMenu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void openMenuItem(int textId) {
        getResponse.getResponseSuccess(textId, action);
    }


}
