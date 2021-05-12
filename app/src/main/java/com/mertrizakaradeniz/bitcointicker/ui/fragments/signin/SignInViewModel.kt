package com.mertrizakaradeniz.bitcointicker.ui.fragments.signin

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import com.mertrizakaradeniz.bitcointicker.utils.Constant.USER_EMAIL
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _signInResult = MutableLiveData<Resource<Task<AuthResult>>>()
    val signInResult: LiveData<Resource<Task<AuthResult>>> = _signInResult

    fun signIn(email: String, password: String) {
        firebaseAuthRepository.signIn(email, password) { resource ->
            _signInResult.postValue(resource)
            if (resource.data?.isSuccessful == true) {
                sharedPreferences.edit().putString(USER_EMAIL, email).apply()
            }
        }
    }
}