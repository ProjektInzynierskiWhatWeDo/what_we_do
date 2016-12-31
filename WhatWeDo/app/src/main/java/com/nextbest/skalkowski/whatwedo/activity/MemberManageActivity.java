package com.nextbest.skalkowski.whatwedo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.interfaces.GetResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberManageActivity extends BasicActivity implements GetResponse , TextWatcher{

    @BindView(R.id.autoCompleteTextViewSearchUser)
    AutoCompleteTextView autoCompleteTextViewSearchUser;
    @BindView(R.id.listViewMembers)
    ListView listViewMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_manage);
        ButterKnife.bind(this);


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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
