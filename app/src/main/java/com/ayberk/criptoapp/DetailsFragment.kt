package com.ayberk.criptoapp

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ayberk.criptoapp.ViewModel.CoinViewModel
import com.ayberk.criptoapp.databinding.FragmentDetailsBinding
import com.ayberk.criptoapp.models.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewDetailsModel: CoinViewModel by viewModels()

    lateinit var resultList: com.ayberk.criptoapp.models.CoinItem
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchMovies()
        Toast.makeText(requireContext(),"Lütfen biraz Bekleyiniz", Toast.LENGTH_SHORT).show()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Lütfen Bekleyin")
        progressDialog.setMessage("Yükleniyor...")
        progressDialog.setCancelable(false) // blocks UI interaction
        progressDialog.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDetailsModel.getObserverLiveData().observe(viewLifecycleOwner,object : Observer<Coin> {
            override fun onChanged(t: Coin?) {
                progressDialog.hide()
                if(t!= null){
                    arguments?.let {
                        val coinid = DetailsFragmentArgs.fromBundle(it).coinId
                        resultList = t[coinid]
                              binding.txtCoinn.text = resultList.symbol
                              binding.txtCoinnask.text = resultList.askPrice
                              binding.txtLast.text = resultList.lastPrice
                              binding.txtBidPrice.text = resultList.bidPrice
                              binding.txtHighprice.text = resultList.highPrice

                }
            }
        }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }
    fun fetchMovies(){

        CoroutineScope(Dispatchers.IO).launch {

            val job1 : Deferred<Unit> = async {
                viewDetailsModel.loadCoins("")
            }

            job1.await()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}