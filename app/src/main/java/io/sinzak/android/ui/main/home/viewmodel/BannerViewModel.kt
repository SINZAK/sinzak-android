package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.remote.dataclass.local.BannerData
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.BannerAdapter
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor() : BaseViewModel() {

    private val bannerMain = BannerData(
        bannerMode = 0,
        bannerDrawableId = R.drawable.ic_banner_main
    )

    private val bannerLogin = BannerData(
        bannerMode = BannerData.BANNER_LOGIN,
        bannerDrawableId = R.drawable.ic_banner_login
    )


    private val banners = mutableListOf(bannerMain, bannerLogin)

    val adapter = BannerAdapter(banners){
        uiModel.gotoLogin()
    }
}