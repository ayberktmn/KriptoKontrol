package com.ayberk.criptoapp

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.criptoapp.Adapter.CoinAdapter
import com.ayberk.criptoapp.ViewModel.CoinViewModel
import com.ayberk.criptoapp.databinding.FragmentHomeBinding
import com.ayberk.criptoapp.models.Coin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var coinAdapter: CoinAdapter
    private lateinit var auth : FirebaseAuth

    val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(CoinViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cikis_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.cikis ->
                requestLogout()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun requestLogout() {
        val builder = AlertDialog.Builder(requireContext())


        builder.setMessage("Uygulamadan çıkış yapmak istediğinizden emin misiniz?")
            .setCancelable(true)
            .setPositiveButton("UYGULAMADAN ÇIKIŞ YAP") { dialog, id ->
                auth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_mainActivity)

            }
            .setNegativeButton("UYGULAMAYA GERİ DÖN") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.rcylerCoin.layoutManager = LinearLayoutManager(requireContext())

        initRecyclerViews()
        fetchCoins()

        viewModel.getObserverLiveData().observe(viewLifecycleOwner, object : Observer<Coin> {
            override fun onChanged(t: Coin?) {
                if(t != null){
                    coinAdapter.setLists(t)
                }
            }
        })

        return view
    }

    fun initRecyclerViews(){

        coinAdapter = CoinAdapter()
        binding.rcylerCoin.adapter = coinAdapter

    }

    fun fetchCoins(){

        CoroutineScope(Dispatchers.IO).launch {

            val job1 : Deferred<Unit> = async {

                viewModel.loadCoins("")
            }
            job1.await()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}