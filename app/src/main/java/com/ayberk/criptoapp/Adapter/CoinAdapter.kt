package com.ayberk.criptoapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.criptoapp.HomeFragmentDirections
import com.ayberk.criptoapp.R
import com.ayberk.criptoapp.models.CoinItem


class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    var liveData : List<CoinItem>? = null
    private var coins: List<CoinItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinAdapter.CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item,parent,false)
        return CoinAdapter.CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinAdapter.CoinViewHolder, position: Int) {
        holder.bind(liveData!!.get(position))
        holder.linearLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(position)
            holder.linearLayout.findNavController().navigate(action)


        }
    }

    override fun getItemCount(): Int {
        if(liveData == null){
            return 0
        }
        else{
            return liveData!!.size
        }
    }

    class CoinViewHolder(val view:View):
        RecyclerView.ViewHolder(view) {

        val txttitle = view.findViewById<TextView>(R.id.txtCoin)
        val txtcoinucret = view.findViewById<TextView>(R.id.txtCoinMoney)
        val imageCoin = view.findViewById<ImageView>(R.id.coinImage)
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayoutCoin)

        fun bind(data:CoinItem){
            txttitle.text = data.symbol
            txtcoinucret.text = data.askPrice

        }
    }


    fun setLists(liveData: List<CoinItem>){
        this.liveData=liveData
        notifyDataSetChanged()
    }
}
