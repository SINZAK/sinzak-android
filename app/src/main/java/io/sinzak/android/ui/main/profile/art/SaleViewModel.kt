package io.sinzak.android.ui.main.profile.art

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val model : ProfileModel
) : BaseViewModel() {

    val isCompleteList = MutableStateFlow(false)

    fun setIsComplete(status : Boolean)
    {
        isCompleteList.value = status
    }

    val adapter = SaleWorkAdapter().apply {
        model.productList.onEach {
            setArts(it)
        }.launchIn(viewModelScope)
    }
}