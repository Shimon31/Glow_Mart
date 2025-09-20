package com.bcsbattle.sbs_e_mart.ui.signUp

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.core.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Sign Up button click
        binding.signUpButton.setOnClickListener {
            val fullName = binding.textInputFullName.text.toString().trim()
            val email = binding.textInputEmail.text.toString().trim()
            val password = binding.textInputPassword.text.toString().trim()
            val confirmPassword = binding.textInputConfirmPassword.text.toString().trim()

            if (validateInput(fullName, email, password, confirmPassword)) {
                registerUser(fullName, email, password)
            }
        }

        // Navigate to Login
        binding.goToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun validateInput(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(requireContext(), "Please enter your full name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Please enter your password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Optionally update the display name
                    val user = firebaseAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Sign up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}