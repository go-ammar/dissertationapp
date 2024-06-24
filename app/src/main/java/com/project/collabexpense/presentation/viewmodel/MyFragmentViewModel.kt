package com.project.collabexpense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collabexpense.domain.model.MyData
import com.project.collabexpense.domain.repository.MyRepository
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyFragmentViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {

    private val _data = MutableStateFlow<Resource<List<MyData>>>(Resource.Loading())
    val data: StateFlow<Resource<List<MyData>>> = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                repository.refreshData()
                repository.getData().collect { data ->
                    _data.value = Resource.Success(data)
                }
            } catch (e: Exception) {
                _data.value = Resource.Error("An error occurred", null)
            }
        }
    }
}