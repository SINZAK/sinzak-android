package io.sinzak.android.ui.main.market.artdetail

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.constants.CODE_USER_REPORT_NAME
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    val model : MarketProductModel,

): BaseViewModel(){

    private var _connect : ArtDetailConnect? = null
    private val connect : ArtDetailConnect
        get() = requireNotNull(_connect)

    fun registerConnect(connect : ArtDetailConnect){
        _connect = connect
    }

    val art get() = model.art

    val imgAdapter = VpAdapter()

    init{

        invokeStateFlow(model.art){art->
            art?.let{
                imgAdapter.imgs = it.imgUrls?: listOf()
                imgAdapter.notifyDataSetChanged()
            }

        }
    }

    fun showMoreDialog(){
        val artist = art.value?.author.toString()
        if(artist == signModel.getUserDisplayName()){
            showEditDialog()
            return
        }
        showReportDialog()
    }


    fun showEditDialog(){
        connect.productEditDialog(
            edit = {

            },
            delete = {
                showDeleteDialog()
            }
        )
    }

    fun showReportDialog(){

        connect.artistReportDialog(
            art.value!!.author,
            onReport = {
                goToReportPage()
            },
            onBlock = {
                showBlockDialog()
            }
        )

    }

    // 차단하기 다이얼로그
    private fun showBlockDialog(){
        connect.artistBlockDialog {
            //block artist
        }

    }

    fun showDeleteDialog(){
        connect.productDeleteDialog {

        }
    }

    // 사용자 신고 페이지로
    private fun goToReportPage(){
        art.value?.let{product->
            Bundle().apply{
                putString(CODE_USER_REPORT_NAME, product.author)
                putString(CODE_USER_REPORT_ID, product.authorId)
                navigation.putBundleData(ReportSendViewModel::class,this)
            }
            navigation.changePage(Page.PROFILE_REPORT_TYPE)
        }


    }



}