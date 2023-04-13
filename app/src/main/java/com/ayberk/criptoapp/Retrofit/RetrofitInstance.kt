package com.ayberk.criptoapp.Retrofit

import com.ayberk.criptoapp.models.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInstance {

    @GET("ticker/24hr")
    fun getCoins(): retrofit2.Call<Coin>
}