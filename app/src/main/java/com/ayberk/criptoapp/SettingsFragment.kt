package com.ayberk.criptoapp

import android.app.Person
import android.database.DatabaseErrorHandler
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ayberk.criptoapp.databinding.FragmentHomeBinding
import com.ayberk.criptoapp.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnKaydet.setOnClickListener {
         /*   if (binding.editText.getText().toString().trim()
                    .equals("") || binding.passwordText.getText().toString().trim().equals("")
            ) {
                Snackbar.make(it, "E-mail veya Şifre boş geçilemez", Snackbar.LENGTH_SHORT).show()
            } */
               // else {
            val user = Firebase.auth.currentUser
            val newPassword = binding.passwordText.text.toString()

            if (user != null && newPassword.isNotEmpty()) {
                val credential = EmailAuthProvider.getCredential(user.email!!, binding.editText.text.toString())
                user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
                    if (reAuthTask.isSuccessful) {
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(requireContext(), "Şifre güncellendi.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(requireContext(), "Şifre güncellenirken bir hata oluştu!.", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Mevcut şifre yanlış!", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Snackbar.make(it, "Boş Geçilemez", Snackbar.LENGTH_SHORT).show()
            }
           // }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

}