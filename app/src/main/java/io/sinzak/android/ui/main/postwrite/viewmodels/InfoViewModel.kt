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

    val isOnBuild = model.isBuildMode


    val title = MutableStateFlow("")
    var content : String = ""
    var price : Int? = null
    val priceInseted = MutableStateFlow(false)


    fun setTitleString(cs : CharSequence){
        title.value = cs.toString()
        model.setTitle(cs.toString())
    }

    fun setContentString(cs : CharSequence){
        content = cs.toString()
        model.setContent(cs.toString())
    }

    fun setPrice(cs : CharSequence){
        try{
            priceInseted.value = cs.toString().isNotEmpty()
            price = cs.toString().toInt()
            model.setPrice(cs.toString().toInt())
        }catch(e:Exception){

        }
    }


    init{
        invokeBooleanFlow(model.flagPrepareBuild){
            insertDefaultData("","",null,false)
        }

        invokeBooleanFlow(model.flagPrepareEdit){
            insertDefaultData(
                model.getTitle(),
                model.getContent(),
                model.getPrice(),
                model.getSuggest()
            )
        }

    }

    private fun insertDefaultData(title : String, content : String, price : Int?, isSuggest : Boolean){
        this.title.value = title
        this.content = content
        this.price = price
        negotiationEnable.value = isSuggest
    }


    fun toggleNegotiation(){
        negotiationEnable.value = !negotiationEnable.value
        model.setSuggestEnable(negotiationEnable.value)
    }

}