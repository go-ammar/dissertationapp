package com.project.collabexpense.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: GroupRepository
) : ViewModel() {


    fun getUserGroups(){
        viewModelScope.launch {
            try {
                repository.getUserGroups().collect{
            }

            }catch (e: Exception){
                Log.e("TAG", "getUserGroups: ", e)
            }
        }
    }

}