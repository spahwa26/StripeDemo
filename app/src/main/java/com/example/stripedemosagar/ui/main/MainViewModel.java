package com.example.stripedemosagar.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stripedemosagar.api_calls.SimpleAPICall;
import com.example.stripedemosagar.interfaces.OnDataLoadListener;
import com.example.stripedemosagar.interfaces.StripeAPI;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainViewModel extends ViewModel {


    private StripeAPI stripeAPI;

    private MutableLiveData<MainResource<ResponseBody>> allCardsData;

    @Inject
    public MainViewModel(StripeAPI stripeAPI) {
        this.stripeAPI = stripeAPI;
    }

    public void init() {
        if (allCardsData != null)
            return;
        allCardsData = new MutableLiveData<>();
    }

    public void getDataFromAPI(HashMap<String, String> getCardsMap, HashMap<String, Object> addUpdateMap, String id, final ApiType type) {

        Call<ResponseBody> call;
        if (type == ApiType.GETCARDS)
            call = stripeAPI.getAllCards(getCardsMap);
        else if (type == ApiType.ADD)
            call = stripeAPI.addCard(addUpdateMap);
        else if (type == ApiType.DELETE)
            call = stripeAPI.deleteCard(id);
        else
            call = stripeAPI.updateCard(id, addUpdateMap);

        SimpleAPICall.getData(type, call, true, true, new OnDataLoadListener() {
            @Override
            public void onDataLoad(ResponseBody body, ApiType type) {
                allCardsData.setValue(MainResource.success(body, type));
            }

            @Override
            public void onFailiure() {
                allCardsData.setValue(MainResource.error("", (ResponseBody) null, type));
            }

            @Override
            public void progressDialog(boolean show) {
                if (show)
                    allCardsData.setValue(MainResource.loading((ResponseBody) null, type));
            }
        });
    }


    public LiveData<MainResource<ResponseBody>> observeCards() {
        return allCardsData;
    }


    public <T> T convertToPojo(Class<T> pojo, String element) {
        return new Gson().fromJson(element, pojo);
    }

}
