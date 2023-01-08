package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    val model : MarketWriteModel
) : BaseViewModel() {


    val marketCategories = mutableListOf("회화일반", "동양화", "조소", "판화", "공예", "기타")
    val outsourcingCategories = mutableListOf("초상화","일러스트","로고/브랜딩","포스터/배너/간판","앱/웹 디자인","인쇄물","패키지/라벨","기타")
    val categorySelected = MutableStateFlow(listOf<String>())

    val currentField = MutableStateFlow(0)

    fun changeField(currentField : Int)
    {
        if(this.currentField.value != currentField)
        {
            this.currentField.value = currentField
            categorySelected.value = listOf()
        }
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
            categorySelected.value.joinToString(separator = ",")
        )
    }




}