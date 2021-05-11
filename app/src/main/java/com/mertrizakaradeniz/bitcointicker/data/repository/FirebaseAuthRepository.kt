package com.mertrizakaradeniz.bitcointicker.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mertrizakaradeniz.bitcointicker.utils.Resource

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    fun getCurrentUser(): String? = firebaseAuth.currentUser?.uid

    fun signUp(email: String, password: String, onResult: (Resource<Task<AuthResult>>) -> Unit) {
        onResult(Resource.Loading())
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(Resource.Success(task))
            } else {
                onResult(Resource.Error("Authentication failed."))
            }
        }
    }

    fun sendEmailVerification(onResult: (Resource<Task<Void>>) -> Unit) {
        onResult(Resource.Loading())
        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(Resource.Success(task))
            } else {
                onResult(Resource.Error(task.exception?.localizedMessage ?: ""))
            }
        }
    }

    fun signIn(email: String, password: String, onResult: (Resource<Task<AuthResult>>) -> Unit) {
        onResult(Resource.Loading())
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(Resource.Success(task))
            } else {
                onResult(Resource.Error(task.exception?.localizedMessage ?: ""))
            }
        }
    }

}