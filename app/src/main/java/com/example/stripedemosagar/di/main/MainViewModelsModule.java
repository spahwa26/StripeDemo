package com.example.stripedemosagar.di.main;

import androidx.lifecycle.ViewModel;

import com.example.stripedemosagar.di.ViewModelKey;
import com.example.stripedemosagar.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindAuthViewModel(MainViewModel viewModel);
}
