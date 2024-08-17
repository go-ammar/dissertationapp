package com.project.collabexpense.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.data.remote.models.Budget
import com.project.collabexpense.data.remote.models.MonthlyCategorySpend
import com.project.collabexpense.domain.repository.BudgetRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _budget = MutableStateFlow<Resource<List<Budget>>>(Resource.Loading())
    val budget: StateFlow<Resource<List<Budget>>> = _budget

    private val _addedBudget = MutableStateFlow<Resource<Budget>>(Resource.Loading())
    val addedBudget: StateFlow<Resource<Budget>> = _addedBudget

    private val _monthlyBudget = MutableStateFlow<Resource<List<MonthlyCategorySpend>>>(Resource.Loading())
    val monthlyBudget: StateFlow<Resource<List<MonthlyCategorySpend>>> = _monthlyBudget

    fun getMonthlyBudgetSpends(){

        viewModelScope.launch {
            try {
                repository.getBudgetsWithData().collect {
                        _monthlyBudget.value = Resource.Success(it)
                    Log.d("TAG", "getBudgets: $it")
                }
            } catch (e: Exception) {
                _budget.value = Resource.Error("Something when wrong", null)
            }
        }

    }

    fun getBudgets() {
        viewModelScope.launch {
            try {
                repository.getBudgets().collect {
                    _budget.value = Resource.Success(it)
                    Log.d("TAG", "getBudgets: $it")
                }
            } catch (e: Exception) {
                _budget.value = Resource.Error("Something when wrong", null)
            }

        }
    }

    fun addBudgets(requestBody: RequestBody) {
        viewModelScope.launch {
            try {
                repository.addBudgets(requestBody).collect {
                    _addedBudget.value = Resource.Success(it)
                    Log.d("TAG", "getBudgets: $it")
                }
            } catch (e: Exception) {
                _addedBudget.value = Resource.Error("Something when wrong", null)
            }


        }
    }
}