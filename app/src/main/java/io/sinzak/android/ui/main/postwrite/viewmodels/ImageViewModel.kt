package io.sinzak.android.ui.main.postwrite.viewmodels

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.WriteConnect
import io.sinzak.android.ui.main.postwrite.adapter.ImageAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    val model: MarketWriteModel,
    val connect: WriteConnect
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

    private fun insertImg(uri: Uri) {
        if (imgUris.size > 4)
            return

        imgUris.add(uri.toString())

        updateCount()

        adapter.notifyDataSetChanged()


    }

    fun onBackPressed(){
        navigation.revealHistory()
    }

    fun loadImage(){

        if (imgUris.size>4){
            uiModel.showToast(valueModel.getString(R.string.str_below_five_image))
            return
        }

        uiModel.loadImage {
            it.forEach { uri ->
                insertImg(uri)
            }
        }
    }

    fun submit() {
        model.setImgUri(imgUris.toMutableList())

        if(model.productType.value == 0)
            navigation.changePage(Page.NEW_POST_INFO)
        else
            navigation.changePage(Page.NEW_POST_WORKINFO)
    }

}