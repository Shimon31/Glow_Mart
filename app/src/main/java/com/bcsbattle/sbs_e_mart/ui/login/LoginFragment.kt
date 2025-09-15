package com.bcsbattle.sbs_e_mart.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.ui.signUp.SignUpFragment
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Login button click
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                loginUser(email, password)
            }
        }


        // Navigate to Sign Up
        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
       /*     requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.action_loginFragment_to_signUpFragment, SignUpFragment())
                .addToBackStack(null)
                .commit()*/
        }

    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                   findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}