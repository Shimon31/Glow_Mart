package com.bcsbattle.sbs_e_mart.ui.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _profileImageUri = MutableLiveData<Uri?>()
    val profileImageUri: LiveData<Uri?> get() = _profileImageUri

    private val storageDir: File = application.filesDir

    // Load saved image if exists
    fun loadProfileImage() {
        val file = File(storageDir, "profile_image.jpg")
        if (file.exists()) {
            _profileImageUri.value = Uri.fromFile(file)
        } else {
            _profileImageUri.value = null
        }
    }

    // Save image locally
    fun saveProfileImage(uri: Uri) {
        val inputStream = getApplication<Application>().contentResolver.openInputStream(uri)
        val file = File(storageDir, "profile_image.jpg")
        inputStream.use { input ->
            file.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        _profileImageUri.value = Uri.fromFile(file)
    }
}
