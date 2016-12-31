package com.nextbest.skalkowski.whatwedo.model;


import android.app.ProgressDialog;
import android.content.Context;

import com.nextbest.skalkowski.whatwedo.R;

public class LoadingPage {
    public static ProgressDialog getLoadingPage(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.CustomDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.layout_loading_page);
        return progressDialog;
    }
}
