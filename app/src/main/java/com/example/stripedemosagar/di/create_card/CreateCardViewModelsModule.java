package com.example.stripedemosagar.di.create_card;

import androidx.lifecycle.ViewModel;

import com.example.stripedemosagar.di.ViewModelKey;
import com.example.stripedemosagar.ui.create_card.CreateCardViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CreateCardViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateCardViewModel.class)
    public abstract ViewModel bindAuthViewModel(CreateCardViewModel viewModel);
}
