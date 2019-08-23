package com.example.stripedemosagar.di;



import com.example.stripedemosagar.di.create_card.CreateCardModule;
import com.example.stripedemosagar.di.create_card.CreateCardScope;
import com.example.stripedemosagar.di.create_card.CreateCardViewModelsModule;
import com.example.stripedemosagar.di.main.MainScope;
import com.example.stripedemosagar.ui.create_card.CreateCardActivity;
import com.example.stripedemosagar.ui.main.MainActivity;
import com.example.stripedemosagar.di.main.MainActivityModule;
import com.example.stripedemosagar.di.main.MainViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class, MainActivityModule.class})
    abstract MainActivity contributeMainActivity();


    @CreateCardScope
    @ContributesAndroidInjector(
            modules = {CreateCardViewModelsModule.class, CreateCardModule.class})
    abstract CreateCardActivity contributeCreateCardActivity();


}
