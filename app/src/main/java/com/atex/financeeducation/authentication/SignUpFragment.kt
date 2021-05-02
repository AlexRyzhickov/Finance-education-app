package com.atex.financeeducation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.databinding.SignUpFragmentBinding
import com.atex.financeeducation.interfaces.AutorizationInterface

class SignUpFragment : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var authInterface: AutorizationInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)

        activity?.let {
            instantiateNavigationInterface(it)
        }

        binding.openSignInFragmentBtn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        binding.signUpBtn.setOnClickListener {
            val nickname = binding.email.text.toString()
            val email = binding.email.text.toString()
            val passwordOne = binding.firstPassword.text.toString()
            val passwordTwo = binding.secondPassword.text.toString()
            if (passwordOne.equals(passwordTwo)) {
                if (passwordOne.length >= 6) {
                    authInterface.signUp(email, passwordOne, nickname)
                }else{
                    Toast.makeText(context,"Пароль должен содержать более 5 символов",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Пароли не совпадают",Toast.LENGTH_SHORT).show()
            }
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