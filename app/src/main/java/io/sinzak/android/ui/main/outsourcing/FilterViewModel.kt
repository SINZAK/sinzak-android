package io.sinzak.android.ui.main.outsourcing

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.FilterAdapter
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : BaseViewModel() {



    val adapter = FilterAdapter(valueModel.categoryWork.toMutableList()) {

    }



}