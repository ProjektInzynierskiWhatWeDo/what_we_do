package com.nextbest.skalkowski.whatwedo.data_model;


import android.util.Log;

import com.nextbest.skalkowski.whatwedo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private int textId;
    private int imageId;
    static Map<Integer, Menu> menu;


    public Menu(int textId, int imageId) {
        this.textId = textId;
        this.imageId = imageId;
    }

    public Menu() {
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    static {
        if(menu == null){
            menu = new HashMap<>();
            menu.put(1,new Menu(R.string.menuAddActivity,R.mipmap.ic_launcher));
            menu.put(2,new Menu(R.string.menuPreferences,R.mipmap.ic_launcher));
            menu.put(3,new Menu(R.string.menuAllEvent,R.mipmap.ic_launcher));
            menu.put(4,new Menu(R.string.menuAddGroup,R.mipmap.ic_launcher));
            menu.put(5,new Menu(R.string.menuMyEvent,R.mipmap.ic_launcher));
            menu.put(6,new Menu(R.string.menuMyGroup,R.mipmap.ic_launcher));
            menu.put(7,new Menu(R.string.menuSettings,R.mipmap.ic_launcher));
            menu.put(8,new Menu(R.string.menuMyProfile,R.mipmap.ic_launcher));
            menu.put(9,new Menu(R.string.menuLogout,R.mipmap.ic_launcher));
        }
    }

    public static ArrayList<Menu> getMenuList(){
        ArrayList<Menu> menuList =
                new ArrayList<Menu>(menu.values());
        return menuList;
    }


}
