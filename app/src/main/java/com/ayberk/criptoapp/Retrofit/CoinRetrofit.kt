package com.ayberk.criptoapp.Retrofit

import androidx.lifecycle.MutableLiveData
import com.ayberk.criptoapp.models.Coin
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class CoinRetrofit @Inject constructor(private val retroService: RetrofitInstance) {

    fun getCoins(liveData: MutableLiveData<Coin>){
        retroService.getCoins().enqueue(object : retrofit2.Callback<Coin>{
            override fun onResponse(call: Call<Coin>, response: Response<Coin>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Coin>, t: Throwable) {
                liveData.postValue(null)
            }
        })
    }
}