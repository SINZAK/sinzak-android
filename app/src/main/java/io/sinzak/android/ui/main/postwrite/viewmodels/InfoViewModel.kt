package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.utils.PriceUtil
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
    val price = MutableStateFlow("0")


    fun setTitleString(cs : CharSequence){
        title.value = cs.toString()
        model.setTitle(cs.toString())
    }

    fun setContentString(cs : CharSequence){
        content = cs.toString()
        model.setContent(cs.toString())
    }

    fun setPrice(cs : CharSequence){
        price.value = cs.toString()
    }


    init{
        invokeBooleanFlow(model.flagPrepareBuild){
            insertDefaultData("","",0,false)
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
        this.price.value = price.toString()
        negotiationEnable.value = isSuggest
    }


    fun toggleNegotiation(){
        negotiationEnable.value = !negotiationEnable.value
        model.setSuggestEnable(negotiationEnable.value)
    }

    fun goSpecPage(){
        model.setPrice(PriceUtil.makePriceInt(price.value))
        navigation.changePage(Page.NEW_POST_SPEC)
    }

}