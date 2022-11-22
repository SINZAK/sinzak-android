package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.FilterAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor() : BaseViewModel() {

    val adapter = FilterAdapter()

    private val _filterList = MutableStateFlow(mutableListOf("회화일반","동양화","조소","판화","공예","기타"))
    val filterList : StateFlow<MutableList<String>> get() = _filterList
    private val _filterChosenList = MutableStateFlow(mutableListOf<String>())
    val filterChosenList : StateFlow<MutableList<String>> get() = _filterChosenList

    val onFilterClick : (String?,Boolean) -> Unit = {filter, status ->

        filter?.run{
            if(status)
            {
                val filters = mutableListOf<String>()
                filters.addAll(filterList.value)
                filters.remove(filter)
                _filterList.value = filters
                val filterCs = mutableListOf<String>()
                filterCs.addAll(_filterChosenList.value)
                filterCs.add(filter)
                _filterChosenList.value = filterCs

            }
            else{
                val filters = mutableListOf<String>()
                filters.addAll(filterList.value)
                filters.add(filter)
                _filterList.value = filters
                val filterCs = mutableListOf<String>()
                filterCs.addAll(_filterChosenList.value)
                filterCs.remove(filter)
                _filterChosenList.value = filterCs
            }
        }?:run{
            val filters = mutableListOf<String>()
            filters.addAll(filterChosenList.value)
            filters.addAll(filterList.value)
            _filterList.value = filters
            _filterChosenList.value = mutableListOf()
        }



    }

}