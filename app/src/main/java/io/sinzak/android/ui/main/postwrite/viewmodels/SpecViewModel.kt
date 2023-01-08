package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SpecViewModel @Inject constructor(
    val model : MarketWriteModel
) : BaseViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage : StateFlow<Int> get() = _currentPage

    private var pWidth = 0
    private var pHeight = 0
    private var pVertical = 0



    fun inputWidthM(cs : CharSequence){
        pWidth = adjustHead(pWidth,cs.toString().toInt())
    }

    fun inputWidthCm(cs : CharSequence){
        pWidth = adjustTail(pWidth,cs.toString().toInt())
    }

    fun inputHeightM(cs : CharSequence){
        pHeight = adjustHead(pHeight,cs.toString().toInt())
    }

    fun inputHeightCm(cs : CharSequence){
        pHeight = adjustTail(pHeight,cs.toString().toInt())
    }

    fun inputVerticalM(cs : CharSequence){
        pVertical = adjustHead(pVertical,cs.toString().toInt())
    }

    fun inputVerticalCm(cs : CharSequence){
        pVertical = adjustTail(pVertical,cs.toString().toInt())
    }



    fun adjustHead(source : Int, new : Int) : Int{
        return source % 100 + new * 100
    }

    fun adjustTail(source : Int, new : Int) : Int{
        return source / 100 * 100 + new
    }


    fun changePage(page:  Int)
    {
        _currentPage.value = page
    }



    fun submit(){
        model.inputDimension(pWidth, pHeight, pVertical)
        model.buildProduct()
    }
}