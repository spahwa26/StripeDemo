package com.example.stripedemosagar.ui.main;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stripedemosagar.adapter.CardAdapter;
import com.example.stripedemosagar.models.CardPojo;
import com.example.stripedemosagar.di.ViewModelProviderFactory;
import com.example.stripedemosagar.ui.create_card.CreateCardActivity;
import com.example.stripedemosagar.models.MainPojo;
import com.example.stripedemosagar.interfaces.OnRecyclerClickListener;
import com.example.stripedemosagar.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import okhttp3.ResponseBody;

public class MainActivity extends DaggerAppCompatActivity implements OnRecyclerClickListener {

    RecyclerView rvCards;
    RelativeLayout progressBar;
    int selectedPosition = -1;
    private ArrayList<CardPojo> cardList = new ArrayList<>();
    private MainViewModel viewModel;

    @Inject
    CardAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);
        viewModel.init();
        findViewById(R.id.btnAddCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CreateCardActivity.class), 101);
            }
        });
        subscribeObserver();
        callObserverForCardsList();
    }

    private void callObserverForCardsList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("object", "card");
        viewModel.getDataFromAPI(map, null, "", ApiType.GETCARDS);
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        rvCards = findViewById(R.id.rvCards);
        rvCards.setLayoutManager(layoutManager);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101)
                adCardToStripe(data);
            else
                updateCardToStripe(data);
        }
    }

    private void updateCardToStripe(Intent data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", data.getStringExtra("name"));
        map.put("address_line1", data.getStringExtra("address"));
        viewModel.getDataFromAPI(null, map, cardList.get(selectedPosition).getId(), ApiType.UPDATE);
    }

    private void adCardToStripe(Intent data) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("source", data.getStringExtra("token"));
        viewModel.getDataFromAPI(null, map, "", ApiType.ADD);
    }

    private void deleteCard() {
        viewModel.getDataFromAPI(null, null, cardList.get(selectedPosition).getId(), ApiType.DELETE);
    }

    private void subscribeObserver( ) {
        viewModel.observeCards().observe(this, new Observer<MainResource<ResponseBody>>() {
            @Override
            public void onChanged(MainResource<ResponseBody> mainPojoMainResource) {
                if (mainPojoMainResource != null) {
                    switch (mainPojoMainResource.status) {

                        case LOADING: {
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case SUCCESS: {
                            progressBar.setVisibility(View.GONE);
                            if (mainPojoMainResource.data != null) {
                                if( mainPojoMainResource.type == ApiType.GETCARDS)
                                    setCardList(mainPojoMainResource.data);
                                else
                                    callObserverForCardsList();
                            }
                            break;
                        }

                        case ERROR: {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }
            }

        });


    }

    private void setCardList(ResponseBody data) {
        MainPojo pojo = null;
        try {
            pojo = viewModel.convertToPojo(MainPojo.class, data.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cardList = pojo.getData();
        adapter.setData(cardList, MainActivity.this);
        rvCards.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(final int pos) {
        selectedPosition = pos;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        String[] animals = {"Update card", "Delete card"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        startActivityForResult(new Intent(MainActivity.this, CreateCardActivity.class).putExtra("card_data", cardList.get(pos)), 102);
                        break;
                    }
                    case 1: {
                        deleteCard();
                        break;
                    }
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void progressClick(View view) {
    }
}
