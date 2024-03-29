package io.sinzak.android.ui.main.postwrite.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.WriteConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ArtWorkViewModel @Inject constructor(
    val model : MarketWriteModel,
    val connect: WriteConnect,
    val productModel: ProductDetailModel
): BaseViewModel() {


    val isOnBuild = model.isBuildMode

    val isWorkBuy = MutableStateFlow(false)

    val title = MutableStateFlow("")
    var content : String = ""

    fun setTitleString(cs : CharSequence){
        title.value = cs.toString()
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
            uiModel.showToast(valueModel.getString(R.string.str_post_success))

            productModel.loadWork(model.getProductId())

            useFlag(productModel.productLoadSuccessFlag){
                connect.gotoDetailAfterBuild()
            }
            model.flagBuildSuccess.value = false

        }

        isWorkBuy.value = model.productType.value == 1

    }

    /**
     * 값 초기화를 수행합니다.
     */
    private fun insertDefaultData(title : String, content : String){
        this.title.value = title
        this.content = content
    }

    fun onBackPressed(){
        connect.navigation.revealHistory()
    }


    /**
     * 의뢰를 등록합니다.
     */
    fun submitData(){
        model.setTitle(title.value)
        model.setContent(content)
        model.doneInput()
    }
}