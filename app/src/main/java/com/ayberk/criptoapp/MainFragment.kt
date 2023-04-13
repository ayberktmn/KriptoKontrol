package com.ayberk.criptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.ayberk.criptoapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.selects.select

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val action = MainFragmentArgs.fromBundle(it).email
            println(action)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.bottomNavigationView2.setOnNavigationItemReselectedListener {}
        binding.bottomNavigationView2.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.anasayfa ->
                    childFragmentManager.primaryNavigationFragment?.findNavController()
                        ?.navigate(R.id.homeFragment)

                R.id.kullanici ->
                    childFragmentManager.primaryNavigationFragment?.findNavController()
                        ?.navigate(R.id.userFragment)

                R.id.ayarlar ->  childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.settingsFragment)

                else -> {

                }
            }
            true
        }
        return view
    }

}