package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SpecViewModel @Inject constructor(
    val model: MarketWriteModel,
    val productModel: MarketProductModel
) : BaseViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPage

    var pWidth = 0
    var pHeight = 0
    var pVertical = 0

    val isOnBuild = model.isBuildMode


    init {
        invokeBooleanFlow(model.flagBuildSuccess) {

            productModel.loadProduct(model.getProductId())
            navigation.removeHistory(Page.NEW_POST_IMAGE)
            navigation.removeHistory(Page.NEW_POST_INFO)
            navigation.removeHistory(Page.NEW_POST)
            navigation.removeHistory(Page.ART_DETAIL)
            navigation.changePage(Page.ART_DETAIL)
            navigation.removeHistory(Page.NEW_POST_SPEC)

            uiModel.showToast("작성 완료")
            model.flagBuildSuccess.value = false

        }


        invokeBooleanFlow(model.flagPrepareEdit) {
            model.getDimension().apply {
                pWidth = get(0)
                pHeight = get(1)
                pVertical = get(2)
            }
        }
        invokeBooleanFlow(model.flagPrepareBuild) {
            pHeight = 0
            pVertical = 0
            pWidth = 0
        }
    }


    /**************************************************************
     *  EditText Action
     *************************************************************/
    fun inputWidthM(cs: CharSequence) {
        pWidth = adjustHead(pWidth, cs.toString().toInt())
    }

    fun inputWidthCm(cs: CharSequence) {
        pWidth = adjustTail(pWidth, cs.toString().toInt())
    }

    fun inputHeightM(cs: CharSequence) {
        pHeight = adjustHead(pHeight, cs.toString().toInt())
    }

    fun inputHeightCm(cs: CharSequence) {
        pHeight = adjustTail(pHeight, cs.toString().toInt())
    }

    fun inputVerticalM(cs: CharSequence) {
        pVertical = adjustHead(pVertical, cs.toString().toInt())
    }

    fun inputVerticalCm(cs: CharSequence) {
        pVertical = adjustTail(pVertical, cs.toString().toInt())
    }


    private fun adjustHead(source: Int, new: Int): Int {
        return source % 100 + new * 100
    }

    private fun adjustTail(source: Int, new: Int): Int {
        return source / 100 * 100 + new
    }


    /**
     * 작품 크기, 종류 페이지 전환
     */
    fun changePage(page: Int) {
        _currentPage.value = page
    }


    /**
     * 프로덕트 제출
     */
    fun submit() {
        model.inputDimension(pWidth, pHeight, pVertical)
        model.doneInput()
    }
}