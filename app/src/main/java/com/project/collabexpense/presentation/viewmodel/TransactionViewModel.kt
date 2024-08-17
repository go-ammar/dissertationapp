package com.project.collabexpense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.data.remote.models.Transaction
import com.project.collabexpense.domain.repository.TransactionRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _categoriesList = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val categoriesList: StateFlow<Resource<List<String>>> = _categoriesList


    private val _transactionList = MutableStateFlow<Resource<List<Transaction>>>(Resource.Loading())
    val transactionList: StateFlow<Resource<List<Transaction>>> = _transactionList

    private val _addedTransaction = MutableStateFlow<Resource<Transaction>>(Resource.Loading())
    val addedTransaction: StateFlow<Resource<Transaction>> = _addedTransaction

    fun getCategories(){
        viewModelScope.launch {
            try {
                repository.getCategories().collect{
                    _categoriesList.value = Resource.Success(it)
                }
            } catch (e: Exception){
                _categoriesList.value = Resource.Error(e.message.toString(), null)
            }
        }
    }

    fun getTransactions(){
        viewModelScope.launch {
            try {
                repository.getTransactions().collect{
                    _transactionList.value = Resource.Success(it)
                }
            } catch (e: Exception){
                _transactionList.value = Resource.Error(e.message.toString(), null)
            }
        }
    }

    fun postTransaction(body: RequestBody){
        viewModelScope.launch {
            try {
                repository.createTransactions(body).collect{
                    _addedTransaction.value = Resource.Success(it)
                }
            } catch (e: Exception){
                _addedTransaction.value = Resource.Error(e.message.toString(), null)
            }
        }
    }
}