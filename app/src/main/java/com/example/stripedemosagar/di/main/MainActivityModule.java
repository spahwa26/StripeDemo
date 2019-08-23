package com.example.stripedemosagar.di.main;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.stripedemosagar.interfaces.StripeAPI;
import com.example.stripedemosagar.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainActivityModule {


    @MainScope
    @Provides
    static StripeAPI provideAuthApi(Retrofit retrofit){
        return retrofit.create(StripeAPI.class);
    }

    @MainScope
    @Provides
    static LinearLayoutManager linearLayoutManager(MainActivity activity){
        return new LinearLayoutManager(activity);
    }
//
//    @MainScope
//    @Provides
//    static CardAdapter cardAdapter(){
//        return new CardAdapter();
//    }
}
