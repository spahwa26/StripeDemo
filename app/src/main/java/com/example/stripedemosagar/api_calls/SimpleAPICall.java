package com.example.stripedemosagar.api_calls;


import com.example.stripedemosagar.interfaces.OnDataLoadListener;
import com.example.stripedemosagar.ui.main.ApiType;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 20-03-2018.
 */

public class SimpleAPICall {


    public static void getData( final ApiType type, Call<ResponseBody> dataCall, final boolean showDialog, final boolean hideDialog, final OnDataLoadListener dataLoadListener) {

        if (showDialog)
            dataLoadListener.progressDialog(true);

        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (hideDialog)
                    dataLoadListener.progressDialog(false);

                if (response.body() != null)
                    dataLoadListener.onDataLoad(response.body(), type);
                else
                    dataLoadListener.onFailiure();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (hideDialog)
                    dataLoadListener.progressDialog(false);

                dataLoadListener.onFailiure();
                t.printStackTrace();
            }
        });


    }
}
