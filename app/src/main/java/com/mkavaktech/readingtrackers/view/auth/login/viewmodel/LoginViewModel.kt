package com.mkavaktech.readingtrackers.view.auth.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mkavaktech.readingtrackers.view.auth.login.model.CustomUserModel
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, navigateTo: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) navigateTo() else Log.d(
                        "SignIn",
                        "signInWithEmailAndPassword: ${it.result}"
                    )
                }
            } catch (ex: Exception) {
                Log.d("SignIn", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun signUpWithEmailAndPassword(email: String, password: String, navigateTo: () -> Unit) =
        viewModelScope.launch {
            if (_loading.value == false) {
                _loading.value = true
                try {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val displayName = it.result.user?.email?.split("@")?.first()
                            createUser(displayName)
                            navigateTo()
                        } else {
                            Log.d("SignIn", "signUpWithEmailAndPassword: ${it.result}")
                        }
                        _loading.value = false
                    }
                } catch (ex: Exception) {
                    Log.d("SignUp", "signUpWithEmailAndPassword: ${ex.message}")
                }
            }
        }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user =
            CustomUserModel(id = null, userId = userId!!, displayName = displayName!!).toMap()
        FirebaseFirestore.getInstance().collection("users").add(user)
    }
}