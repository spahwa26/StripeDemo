package com.example.stripedemosagar.interfaces;


import com.example.stripedemosagar.ui.main.ApiType;

import okhttp3.ResponseBody;

/**
 * Created by User on 20-03-2018.
 */

public interface OnDataLoadListener {


    void onDataLoad(ResponseBody body, ApiType type);

    void onFailiure();

    void progressDialog(boolean show);

}
