package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import io.sinzak.android.ui.main.home.adapter.ArtistAdapter
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor() : HomeLinearViewModel() {
    override val adapter = ArtReferAdapter(){

    }
    override val hMargin: Float
        get() = 20f

    override var title: String
            = valueModel.getString(R.string.str_home_artist_title)

}