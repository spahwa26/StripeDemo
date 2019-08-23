package com.example.stripedemosagar.ui.create_card;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import javax.inject.Inject;

public class CreateCardViewModel extends ViewModel {


    private Stripe stripe;

    private MutableLiveData<CreateCardResource<Intent>> tokenData;

    @Inject
    public CreateCardViewModel(Stripe stripe) {
        this.stripe = stripe;
    }


    public void init() {
        if (tokenData != null)
            return;
        tokenData = new MutableLiveData<>();
    }


    public void createToken(final Card card) {
        tokenData.setValue(CreateCardResource.loading((Intent) null));
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(@NonNull Token token) {
                        // send tokenData id to your server for charge creation
                        Intent intent = new Intent();


                        intent.putExtra("number", card.getNumber());
                        intent.putExtra("exp_month", card.getExpMonth());
                        intent.putExtra("exp_year", card.getExpYear());
                        intent.putExtra("token", token.getId());
                        intent.putExtra("cvc", card.getCVC());

                        tokenData.setValue(CreateCardResource.success(intent));
                    }

                    public void onError(@NonNull Exception error) {
                        tokenData.setValue(CreateCardResource.error("",(Intent) null));
                    }
                }
        );
    }

    public LiveData<CreateCardResource<Intent>> observeTokenData() {
        return tokenData;
    }

}
