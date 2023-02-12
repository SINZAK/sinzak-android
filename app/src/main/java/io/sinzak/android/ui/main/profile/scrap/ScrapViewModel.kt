package io.sinzak.android.ui.main.profile.scrap

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor() : BaseViewModel() {

    fun backPressed()
    {
        navigation.revealHistory()
    }
}