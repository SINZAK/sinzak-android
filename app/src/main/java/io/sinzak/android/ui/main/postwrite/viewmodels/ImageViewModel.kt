package io.sinzak.android.ui.main.postwrite.viewmodels

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.adapter.ImageAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    val model : MarketWriteModel
) : BaseViewModel() {

    val imgUris : MutableList<Uri> = mutableListOf()

    fun initData(){
        imgUris.clear()
        imgUris.addAll(model.imgUris)
        adapter.notifyDataSetChanged()
        updateCount()
    }

    val adapter = ImageAdapter(imgUris){

        imgUris.remove(it)
        updateCount()
    }

    val imgCnt = MutableStateFlow(0)

    val canGoNext = MutableStateFlow(false)


    private fun updateCount(){
        imgCnt.value = imgUris.size
        canGoNext.value = imgUris.isNotEmpty()
    }

    fun insertImg(uri : Uri)
    {
        if(imgUris.size > 4)
            imgUris.removeAt(0)

        imgUris.add(uri)

        updateCount()



        adapter.notifyDataSetChanged()



    }

    fun submit(){
        model.setImgUri(imgUris.toMutableList())
    }

}