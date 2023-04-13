package com.ayberk.criptoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.criptoapp.Retrofit.CoinRetrofit
import com.ayberk.criptoapp.models.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(private val repo: CoinRetrofit) : ViewModel() {

    var coinList: MutableLiveData<Coin>


    init {
        coinList = MutableLiveData()

    }

    fun getObserverLiveData(): MutableLiveData<Coin> {
        return coinList
    }


    fun  loadCoins(page:String){

        repo.getCoins(coinList)

    }

}