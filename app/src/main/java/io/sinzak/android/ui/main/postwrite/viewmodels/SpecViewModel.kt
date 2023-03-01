package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.WriteConnect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SpecViewModel @Inject constructor(
    val model: MarketWriteModel,
    val productModel: ProductDetailModel,
    val connect: WriteConnect
) : BaseViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPage

    var pWidth = 0
    var pHeight = 0
    var pVertical = 0

    val isOnBuild = model.isBuildMode


    init {
        invokeBooleanFlow(model.flagBuildSuccess) {
            uiModel.showToast("작성 완료")

            productModel.loadProduct(model.getProductId())
            connect.gotoDetailAfterBuild()

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
        pWidth = adjustHead(pWidth, cs.toString())
    }

    fun inputWidthCm(cs: CharSequence) {
        pWidth = adjustTail(pWidth, cs.toString())
    }

    fun inputHeightM(cs: CharSequence) {
        pHeight = adjustHead(pHeight, cs.toString())
    }

    fun inputHeightCm(cs: CharSequence) {
        pHeight = adjustTail(pHeight, cs.toString())
    }

    fun inputVerticalM(cs: CharSequence) {
        pVertical = adjustHead(pVertical, cs.toString())
    }

    fun inputVerticalCm(cs: CharSequence) {
        pVertical = adjustTail(pVertical, cs.toString())
    }


    private fun adjustHead(source: Int, new: String): Int {
        var newInt = new.toIntOrNull()
        if (newInt==null) newInt = 0
        return source % 100 + newInt * 100
    }

    private fun adjustTail(source: Int, new: String): Int {
        var newInt = new.toIntOrNull()
        if (newInt==null) newInt = 0
        return source / 100 * 100 + newInt
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