package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    val model : MarketWriteModel
) : BaseViewModel() {


    val marketCategories = valueModel.categoryMarket.toMutableList()
    val outsourcingCategories = valueModel.categoryWork.toMutableList()
    val categorySelected = MutableStateFlow(listOf<String>())

    val currentField = MutableStateFlow(0)

    fun changeField(currentField : Int)
    {
        model.setProductType(currentField)
        categorySelected.value = listOf()
    }


    fun clickCategory(category : String, status : Boolean)
    {
        val list = categorySelected.value.toMutableList()
        if(status)
            list.remove(category)
        else if(list.size < 3)
            list.add(category)
        marketCategories.sortBy {
            !list.contains(it)
        }
        outsourcingCategories.sortBy {
            !list.contains(it)
        }
        categorySelected.value = list

    }

    fun submit(){
        model.setCategoryText(
            valueModel.makeRequestStr(categorySelected.value)
        )
    }


    init{
        invokeBooleanFlow(model.flagPrepareBuild){
            categorySelected.value = listOf()
        }
    }



}