package io.sinzak.android.ui.main.market.artdetail

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.constants.CODE_USER_REPORT_NAME
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    val model : MarketProductModel,

): BaseViewModel(){

    private var _connect : ArtDetailConnect? = null
    private val connect : ArtDetailConnect
        get() = requireNotNull(_connect)


    /**
     * 액티비티에 필요한 기능을 요청하는 connect 클래스
     */
    fun registerConnect(connect : ArtDetailConnect){
        _connect = connect
    }

    /**
     * 현재 조회중인 작품
     */
    val art get() = model.art


    /**
     * 내가 올린 프로덕트인가 ? ( 로그인 토큰 필요 )
     */
    val isMyProduct = MutableStateFlow(false)

    /**
     * 내가 구매한 프로덕트인가 ? ( 로그인 토큰 필요 )
     */
    val isBought = MutableStateFlow(false)

    /**
     * 좋아요 한 프로덕트인가?
     */
    val isLike = MutableStateFlow(false)
    /**
     * 좋아요 갯수
     */
    val likeCnt = MutableStateFlow(0)
    /**
     * 찜한 프로덕트인가?
     */
    val isWish = MutableStateFlow(false)
    /**
     * 찜한 개수
     */
    val wishCnt = MutableStateFlow(0)

    /**
     * 상품 id
     */
    private var product = -1

    val imgAdapter = VpAdapter()

    init{
        collectArt()

    }

    /**
     * 작품 데이터를 구독한다. 필요한 데이터를 분리해서 state 를 저장한다.
     */
    private fun collectArt(){
        invokeStateFlow(model.art){art->
            art?.let{
                imgAdapter.imgs = it.imgUrls?: listOf()
                imgAdapter.notifyDataSetChanged()

                isLike.value = it.like
                likeCnt.value = it.likeCnt
                isWish.value = it.wish
                wishCnt.value = it.wishCnt
                product = it.productId

                isMyProduct.value = it.authorId == profileModel.getUserId()
            }

        }

    }

    /*********************************************************************
     * Click Event
     **********************************************************************/

    /**
     * Like 버튼을 눌렀을때 동작
     */
    fun toggleLike(){
        model.postProductLike(product,!isLike.value)
        isLike.value = !isLike.value
        likeCnt.value = likeCnt.value + if(isLike.value) 1 else -1
    }


    /**
     * Wish 버튼을 눌렀을때 동작
     */
    fun toggleWish(){
        model.postProductWish(product,!isWish.value)
        isWish.value = !isWish.value
        wishCnt.value = wishCnt.value + if(isWish.value) 1 else -1
    }

    /**
     * 더보기 버튼을 눌렀을때 동작
     */
    fun showMoreDialog(){
        if(isMyProduct.value){
            showEditDialog()
            return
        }
        showReportDialog()
    }

    /**
     * 채팅방 버튼을 눌렀을때 동작
     */
    fun onClickChat(){

    }

    /**
     * 가격 제안하기 버튼 눌렀을때 동작
     */
    fun onClickSuggest(){

    }



    /***********************************************************************
     * Dialog Show
     **********************************************************************/


    /**
     * 게시글 수정 다이알로그 열기
     */
    private fun showEditDialog(){
        connect.productEditDialog(
            edit = {

            },
            delete = {
                showDeleteDialog()
            }
        )
    }

    /**
     * 작가 신고 / 차단 다아일로그 열기
     */
    private fun showReportDialog(){

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

    /**
     * 작가 차단하기 다이알로그
     */
    private fun showBlockDialog(){
        connect.artistBlockDialog {
            //block artist
        }

    }

    /**
     * 작품 삭제 다이알로그 열기
     */
    private fun showDeleteDialog(){
        connect.productDeleteDialog {

        }
    }

    /**
     * 사용자 신고 페이지 가기
     */
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