package com.bcsbattle.sbs_e_mart.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bcsbattle.sbs_e_mart.MainActivity
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: ProfileViewModel by viewModels()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.saveProfileImage(it)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        // Set user info
        currentUser?.let { user ->
            binding.userEmailText.text = user.email ?: "No Email"
            binding.userNameText.text = user.displayName ?: "User"
        }

        // Observe profile image
        viewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.profileImage.setImageURI(uri)
            } else {
                binding.profileImage.setImageResource(R.drawable.placeholder) // default image
            }
        }

        // Load saved image
        viewModel.loadProfileImage()

        // Click to upload photo
        binding.profileImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Logout button click
        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
