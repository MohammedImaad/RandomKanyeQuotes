package com.example.android.randomkanyequotes.Retrofit;

import com.example.android.randomkanyequotes.Retrofit.KanyeQuote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("https://api.kanye.rest")
    Call<KanyeQuote> getQuote();
}
