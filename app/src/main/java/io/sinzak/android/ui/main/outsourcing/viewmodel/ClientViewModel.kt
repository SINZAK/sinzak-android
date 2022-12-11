package io.sinzak.android.ui.main.outsourcing.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor() : BaseViewModel() {
    val adapter = ArtsAdapter()
}