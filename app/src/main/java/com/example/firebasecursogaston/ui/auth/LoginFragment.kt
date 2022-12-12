package com.example.firebasecursogaston.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.firebasecursogaston.R
import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.core.hideKeyboard
import com.example.firebasecursogaston.data.remote.auth.AuthDataSource
import com.example.firebasecursogaston.databinding.FragmentLoginBinding
import com.example.firebasecursogaston.domain.auth.AuthRepoImpl
import com.example.firebasecursogaston.presentation.auth.AuthViewModel
import com.example.firebasecursogaston.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(AuthDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            if(it.displayName.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
            } else {
                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
            }
        }
    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            it.hideKeyboard()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun goToSignUpPage(){
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun signIn(email:String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
            }
        })
    }

    private fun validateCredentials(email: String, password:String) {
        if(email.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return
        }
        if(password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return
        }

    }

}