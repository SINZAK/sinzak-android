package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.FilterAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(
    val marketArtModel: MarketArtModel
) : BaseViewModel() {

    val adapter = FilterAdapter(mutableListOf("회화일반", "동양화", "조소", "판화", "공예", "기타")) {
        marketArtModel.setCategoryString(it.joinToString(separator = ","))
    }



}