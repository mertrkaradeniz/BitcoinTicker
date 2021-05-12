package com.mertrizakaradeniz.bitcointicker.ui.fragments.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _signUpResult = MutableLiveData<Resource<Task<AuthResult>>>()
    val signUpResult: LiveData<Resource<Task<AuthResult>>> = _signUpResult

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuthRepository.signUp(email, password) { resource ->
                _signUpResult.postValue(resource)
            }
        }
    }
}