package com.example.stripedemosagar.ui.create_card;

import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stripedemosagar.R;
import com.example.stripedemosagar.models.CardPojo;
import com.example.stripedemosagar.di.ViewModelProviderFactory;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CreateCardActivity extends DaggerAppCompatActivity {

    RelativeLayout progressBar;
    CardInputWidget cardInputWidget;
    EditText etName, etAddress;

    @Inject
    Stripe stripe;

    @Inject
    ViewModelProviderFactory providerFactory;

    private CreateCardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        initViews();

        viewModel = ViewModelProviders.of(this, providerFactory).get(CreateCardViewModel.class);
        viewModel.init();

        getIntentData();

        subscribeObserver();
    }

    private void subscribeObserver() {
        viewModel.observeTokenData().observe(this, new Observer<CreateCardResource<Intent>>() {
            @Override
            public void onChanged(CreateCardResource<Intent> intentCreateCardResource) {
                if (intentCreateCardResource != null) {
                    switch (intentCreateCardResource.status) {

                        case LOADING: {
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case SUCCESS: {
                            progressBar.setVisibility(View.GONE);
                            if (intentCreateCardResource.data != null) {
                                setResult(RESULT_OK, intentCreateCardResource.data);

                                finish();
                            }
                            break;
                        }

                        case ERROR: {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CreateCardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("card_data")) {
            CardPojo pojo = (CardPojo) getIntent().getSerializableExtra("card_data");
            cardInputWidget.setCardNumber(pojo.getLast4());
            cardInputWidget.setExpiryDate(pojo.getExp_month(), pojo.getExp_year());
            cardInputWidget.setEnabled(false);
            etName.setText(pojo.getName());
            etAddress.setText(pojo.getAddress_line1());

            findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("name", etName.getText().toString());
                    intent.putExtra("address", etAddress.getText().toString());
                    setResult(RESULT_OK, intent);

                    finish();
                }
            });
        } else
            findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Card card = cardInputWidget.getCard();

                    if (card != null && card.validateCard()) {
                        card = card.toBuilder().name(etName.getText().toString()).build();
                        card = card.toBuilder().addressLine1(etAddress.getText().toString()).build();
                        viewModel.createToken(card);
                    }
                    else
                        Toast.makeText(CreateCardActivity.this, "Please fill the details.", Toast.LENGTH_LONG).show();



                }
            });
    }

    private void initViews() {
        progressBar=findViewById(R.id.progressBar);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        cardInputWidget = findViewById(R.id.card_input_widget);
    }


    public void progressClick(View view) {

    }
}
