package com.tarikonal.retrofitjava.service;

import com.tarikonal.retrofitjava.model.CyrptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    @GET("prices?key=2d9524b21677a7571b5ef0c7b6a57eea")
    Observable<List<CyrptoModel>> getData();
    //Call<List<CyrptoModel>> getData();
}
