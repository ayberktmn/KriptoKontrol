package com.ayberk.criptoapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ayberk.criptoapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null){
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtkayit.setOnClickListener {

            if (binding.editTextemail.getText().toString().trim().equals("") || binding.editTextsifre.getText().toString().trim().equals("")) {
                Snackbar.make(it,"E-mail veya Şifre boş geçilemez", Snackbar.LENGTH_SHORT).show()

            } else {
                auth.createUserWithEmailAndPassword(
                    binding.editTextemail.text.toString(),
                    binding.editTextsifre.text.toString()


                ).addOnSuccessListener {

                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    action.email = binding.editTextemail.text.toString()
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Kayıt Başarılı!", Toast.LENGTH_LONG).show()

                    // botnav.visibility = View.VISIBLE

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Kayıt Başarısız!", Toast.LENGTH_LONG).show()

                }

            }

        }

        binding.txtgiris.setOnClickListener {
            if (binding.editTextemail.getText().toString().trim().equals("") || binding.editTextsifre.getText().toString().trim().equals("")) {

                Snackbar.make(it,"E-mail veya Şifre boş geçilemez", Snackbar.LENGTH_SHORT).show()

            } else {
                auth.signInWithEmailAndPassword(
                    binding.editTextemail.text.toString(),
                    binding.editTextsifre.text.toString()
                ).addOnSuccessListener {

                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    action.email = binding.editTextemail.text.toString()
                    println(action)
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Hoşgeldiniz", Toast.LENGTH_LONG).show()
                    // botnav.visibility = View.VISIBLE

                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Bu Kullanıcı Kayıtlı Değil",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
