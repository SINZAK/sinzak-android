package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class InfoViewModel @Inject constructor(
    val model : MarketWriteModel
) : BaseViewModel() {

    val negotiationEnable = MutableStateFlow(false)


    fun setTitleString(cs : CharSequence){
        model.setTitle(cs.toString())
    }

    fun setContentString(cs : CharSequence){
        model.setContent(cs.toString())
    }

    fun setPrice(cs : CharSequence){
        try{
            model.setPrice(cs.toString().toInt())
        }catch(e:Exception){

        }
    }




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