package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SpecViewModel @Inject constructor() : BaseViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage : StateFlow<Int> get() = _currentPage


    fun changePage(page:  Int)
    {
        _currentPage.value = page
    }
}