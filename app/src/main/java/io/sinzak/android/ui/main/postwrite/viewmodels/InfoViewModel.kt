package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class InfoViewModel @Inject constructor() : BaseViewModel() {

    val negotiationEnable = MutableStateFlow(false)


    fun toggleNegotiation(){
        if(negotiationEnable.value)
        {
            negotiationEnable.value = false

        }
        else{
            negotiationEnable.value = true
        }
    }

}