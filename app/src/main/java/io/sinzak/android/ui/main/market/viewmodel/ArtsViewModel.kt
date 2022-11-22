package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(val md : MarketArtModel) : BaseViewModel() {


    val adapter = ArtsAdapter()
}