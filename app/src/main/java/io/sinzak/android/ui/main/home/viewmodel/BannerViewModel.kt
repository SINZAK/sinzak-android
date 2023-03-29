package io.sinzak.android.ui.main.home.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.remote.dataclass.local.BannerData
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.BannerAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    val model : HomeProductModel
) : BaseViewModel() {

    private val banners = mutableListOf<BannerData>()

    init{
        model.banners.onEach {
            banners.clear()
            banners.addAll(it)
            adapter.notifyDataSetChanged()
        }.launchIn(viewModelScope)
    }

    val adapter = BannerAdapter(banners){
        profileModel.changeProfile(newUserId = it)
        navigation.changePage(Page.PROFILE_OTHER)
    }
}