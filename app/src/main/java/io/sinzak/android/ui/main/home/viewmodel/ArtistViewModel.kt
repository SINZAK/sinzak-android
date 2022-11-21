package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import io.sinzak.android.ui.main.home.adapter.ArtistAdapter
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor() : BaseViewModel() {
    val adapter = ArtistAdapter()

}