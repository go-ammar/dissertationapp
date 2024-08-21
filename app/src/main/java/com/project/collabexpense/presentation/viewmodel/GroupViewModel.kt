package com.project.collabexpense.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.data.remote.models.GroupData
import com.project.collabexpense.data.remote.models.GroupDetails
import com.project.collabexpense.data.remote.models.GroupTransactionAdded
import com.project.collabexpense.domain.repository.GroupRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: GroupRepository
) : ViewModel() {

    private val _userGroupList = MutableStateFlow<Resource<List<GroupData>>>(Resource.Loading())
    val userGroupList: StateFlow<Resource<List<GroupData>>> = _userGroupList

    private val _userGroupCreated = MutableStateFlow<Resource<GroupData>>(Resource.Loading())
    val userGroupCreated: StateFlow<Resource<GroupData>> = _userGroupCreated

    private val _groupDetails = MutableStateFlow<Resource<GroupDetails>>(Resource.Loading())
    val groupDetails: StateFlow<Resource<GroupDetails>> = _groupDetails

    private val _groupTransactionAdded = MutableStateFlow<Resource<GroupTransactionAdded>>(Resource.Loading())
    val groupTransactionAdded: StateFlow<Resource<GroupTransactionAdded>> = _groupTransactionAdded

    fun getUserGroups(){
        viewModelScope.launch {
            try {
                repository.getUserGroups().collect{
                    _userGroupList.value = Resource.Success(it)
                }

            }catch (e: Exception){
                Log.e("TAG", "getUserGroups: ", e)
            }
        }
    }

    fun createGroup(requestBody: RequestBody){
        viewModelScope.launch {
            try {
                repository.createUserGroup(requestBody).collect{
                    _userGroupCreated.value = Resource.Success(it)
                }

            }catch (e: Exception){
                Log.e("TAG", "getUserGroups: ", e)
            }
        }
    }

    fun getGroupDetails(groupId: Long){
        viewModelScope.launch {
            try {
                repository.getGroupDetails(groupId).collect{
                    _groupDetails.value = Resource.Success(it)
                }

            }catch (e: Exception){
                Log.e("TAG", "getUserGroups: ", e)
            }
        }
    }

    fun addTransaction(requestBody: RequestBody){
        viewModelScope.launch {
            try {
                repository.addGroupTransaction(requestBody).collect{
                    _groupTransactionAdded.value = Resource.Success(it)
                }

            }catch (e: Exception){
                Log.e("TAG", "getUserGroups: ", e)
            }
        }
    }
}