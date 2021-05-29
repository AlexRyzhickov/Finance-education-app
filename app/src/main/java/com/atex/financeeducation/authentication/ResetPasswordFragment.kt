package com.atex.financeeducation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.databinding.ResetPasswordFragmentBinding
import com.example.androidkeyboardstatechecker.showToast
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private var _binding: ResetPasswordFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResetPasswordFragmentBinding.inflate(inflater, container, false)

        binding.backToSignInFragmentBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.resetPassBtn.setOnClickListener {
            if (!binding.email.text.toString().trim().equals("")){

                val emailAddress = binding.email.text.toString().trim()
                auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            context?.showToast("На указанную почту отправлено письмо для сброса пароля")
                            findNavController().popBackStack()
                        }
                    }
            }else{
                context?.showToast("Пустое поле почтового адреса")
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}