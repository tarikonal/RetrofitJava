package com.tarikonal.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tarikonal.retrofitjava.R;
import com.tarikonal.retrofitjava.adapter.RecyclerViewAdapter;
import com.tarikonal.retrofitjava.model.CyrptoModel;
import com.tarikonal.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
ArrayList<CyrptoModel> cyrptoModels;
private String BASE_URL="https://api.nomics.com/v1/";
Retrofit retrofit;
RecyclerView recyclerView;
RecyclerViewAdapter recyclerViewAdapter;
CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://api.nomics.com/v1/prices?key=2d9524b21677a7571b5ef0c7b6a57eea
        recyclerView = findViewById(R.id.recyclerView);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();
    }

    private void loadData(){
       final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
       compositeDisposable = new CompositeDisposable();
       compositeDisposable.add(cryptoAPI.getData()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this::handleResponse));
        /*Call<List<CyrptoModel>> call = cryptoAPI.getData();
        call.enqueue(new Callback<List<CyrptoModel>>() {
            @Override
            public void onResponse(Call<List<CyrptoModel>> call, Response<List<CyrptoModel>> response) {
                if(response.isSuccessful()){
                    List<CyrptoModel> responseList = response.body();
                    cyrptoModels = new ArrayList<>(responseList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cyrptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    for (CyrptoModel cyrptoModel:cyrptoModels) {
                        System.out.println(cyrptoModel.currency +":"+ cyrptoModel.price);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CyrptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private void handleResponse(List<CyrptoModel> cyrptoModelList)
    {
        cyrptoModels = new ArrayList<>(cyrptoModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cyrptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
            super.onDestroy();
            compositeDisposable.clear();
    }
}
