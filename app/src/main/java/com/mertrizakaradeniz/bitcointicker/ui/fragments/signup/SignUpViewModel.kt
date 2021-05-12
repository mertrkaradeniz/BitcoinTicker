package com.mertrizakaradeniz.bitcointicker.ui.fragments.signup

import androidx.lifecycle.ViewModel
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

}