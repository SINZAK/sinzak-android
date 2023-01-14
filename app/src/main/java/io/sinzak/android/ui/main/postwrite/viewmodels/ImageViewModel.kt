package io.sinzak.android.ui.main.postwrite.viewmodels

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_ON_EDIT
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.adapter.ImageAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    val model: MarketWriteModel
) : BaseViewModel() {
    private val imgUris: MutableList<String> = mutableListOf()

    val adapter = ImageAdapter(imgUris) {

        imgUris.remove(it)
        updateCount()
    }
    val imgCnt = MutableStateFlow(0)

    val canGoNext = MutableStateFlow(false)

    val isOnBuild = model.isBuildMode


    /**
     * 상태 초기화
     */
    fun initData() {


        imgUris.clear()
        imgUris.addAll(model.imgUris)

        adapter.notifyDataSetChanged()
        updateCount()


        /**
         * Edit mode일 경우 수정
         */


    }






    init{

        invokeBooleanFlow(model.flagPrepareBuild){
            adapter.thumbRemovable = true
            adapter.notifyDataSetChanged()
        }

        invokeBooleanFlow(model.flagPrepareEdit){
            adapter.thumbRemovable = false
            adapter.notifyDataSetChanged()
        }
    }




    private fun updateCount() {
        imgCnt.value = imgUris.size
        canGoNext.value = imgUris.isNotEmpty()
    }

    fun insertImg(uri: Uri) {
        if (imgUris.size > 4)
            imgUris.removeAt(0)

        imgUris.add(uri.toString())

        updateCount()



        adapter.notifyDataSetChanged()


    }

    fun submit() {
        model.setImgUri(imgUris.toMutableList())
    }

}