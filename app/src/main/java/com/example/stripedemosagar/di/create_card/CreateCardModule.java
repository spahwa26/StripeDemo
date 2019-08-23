package com.example.stripedemosagar.di.create_card;

import com.example.stripedemosagar.ui.create_card.CreateCardActivity;
import com.stripe.android.Stripe;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateCardModule {
    @CreateCardScope
    @Provides
    static Stripe provideStripe(CreateCardActivity activity){
        return new Stripe(activity, "publish_key_here");
    }
}
