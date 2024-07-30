package com.project.collabexpense.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.data.remote.models.UserInfo
import com.project.collabexpense.domain.repository.EditProfileRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: EditProfileRepository
) : ViewModel() {

    private val _data = MutableStateFlow<Resource<UserInfo>>(Resource.Loading())
    val data: StateFlow<Resource<UserInfo>> = _data

    private val _updatedUser = MutableStateFlow<Resource<UserInfo>>(Resource.Loading())
    val updatedUser: StateFlow<Resource<UserInfo>> = _updatedUser

    fun getUserDetails() {
        viewModelScope.launch {
            try {
                repository.getDetails().collect {
                    _data.value = Resource.Success(it)
                    Log.d("TAG", "getUserDetails: $it")
                }
            } catch (e: Exception) {
                _data.value = Resource.Error(e.message.toString(), null)
            }

        }
    }

    fun updateUserDetails(body: RequestBody) {
        viewModelScope.launch {
            try {
                repository.updateDetails(body).collect {
                    _updatedUser.value = Resource.Success(it)
                    Log.d("TAG", "getUserDetails Updated: $it")
                }
            } catch (e: Exception) {
                _updatedUser.value = Resource.Error(e.message.toString(), null)
            }

        }
    }
}