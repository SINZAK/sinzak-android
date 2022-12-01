package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.adapter.ProfileArtAdapter
import javax.inject.Inject

@HiltViewModel
class ProfileWorkViewModel @Inject constructor(): BaseViewModel() {
    val adapter = ProfileArtAdapter()
}