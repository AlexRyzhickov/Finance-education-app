package com.atex.financeeducation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.databinding.SignInFragmentBinding
import com.atex.financeeducation.interfaces.AutorizationInterface

class SignInFragment : Fragment() {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var authInterface: AutorizationInterface


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInFragmentBinding.inflate(inflater,container,false)

        activity?.let{
            instantiateNavigationInterface(it)
        }

        binding.openSignUpFragmentBtn.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.signInBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            authInterface.signIn(email, password)
        }

        return binding.root
    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        authInterface = context as AutorizationInterface
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}