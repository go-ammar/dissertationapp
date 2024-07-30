package com.project.collabexpense.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.data.remote.models.AuthResponse
import com.project.collabexpense.data.remote.models.UserInfo
import com.project.collabexpense.domain.repository.AuthRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {


    private val _dataLocal = MutableStateFlow<Resource<AuthResponse>>(Resource.Loading())
    val dataLocal: StateFlow<Resource<AuthResponse>> = _dataLocal

    private val _dataSignup = MutableStateFlow<Resource<UserInfo>>(Resource.Loading())
    val dataSignup: StateFlow<Resource<UserInfo>> = _dataSignup

    suspend fun loginAuth(requestBody: RequestBody) {
        Log.d("TAG", "loginAuth: $requestBody")
        viewModelScope.launch {
            try {
                repository.login(requestBody).collect {
                    Log.d("TAG", "loginAuth: $it")
                    _dataLocal.value = Resource.Success(it)
                }
            } catch (e: Exception) {
                _dataLocal.value = Resource.Error(e.message.toString(), null)
                Log.d("TAG", "loginAuth: " + e.message.toString())
            }
        }
    }

    suspend fun signUpAuth(requestBody: RequestBody) {
        viewModelScope.launch {
            try {
                repository.signUp(requestBody).collect {
                    Log.d("TAG", "loginAuth: $it")
                    _dataSignup.value = Resource.Success(it)
                }
            } catch (e: Exception) {
                _dataSignup.value = Resource.Error(e.message.toString(), null)
                Log.d("TAG", "loginAuth: " + e.message.toString())
            }
        }
    }

}