package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.WriteConnect
import javax.inject.Inject

@HiltViewModel
class ArtWorkViewModel @Inject constructor(
    val model : MarketWriteModel,
    val connect: WriteConnect,
    val productModel: ProductDetailModel
): BaseViewModel() {


    val isOnBuild = model.isBuildMode


    var title : String = ""
    var content : String = ""

    fun setTitleString(cs : CharSequence){
        title = cs.toString()
        model.setTitle(cs.toString())
    }

    fun setContentString(cs : CharSequence){
        content = cs.toString()
        model.setContent(cs.toString())
    }


    /**
     * 값 초기화
     */
    init{
        invokeBooleanFlow(model.flagPrepareBuild){
            insertDefaultData("","")
        }
        invokeBooleanFlow(model.flagPrepareEdit){
            insertDefaultData(
                model.getTitle(),
                model.getContent(),
            )
        }

        invokeBooleanFlow(model.flagBuildSuccess) {
            uiModel.showToast("작성 완료")

            productModel.loadWork(model.getProductId())
            connect.gotoDetailAfterBuild()

            model.flagBuildSuccess.value = false

        }

    }

    /**
     * 값 초기화를 수행합니다.
     */
    private fun insertDefaultData(title : String, content : String){
        this.title = title
        this.content = content
    }

    fun onBackPressed(){
        connect.navigation.revealHistory()
    }


    /**
     * 의뢰를 등록합니다.
     */
    fun submitData(){
        model.setTitle(title)
        model.setContent(content)
        model.doneInput()
    }
}