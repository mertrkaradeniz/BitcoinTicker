package com.mertrizakaradeniz.bitcointicker.ui.fragments.signin

import androidx.lifecycle.ViewModel
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

}