package io.sinzak.android.ui.main.market.artdetail

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.constants.TYPE_MARKET_PRODUCT
import io.sinzak.android.enums.Page
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    val model: ProductDetailModel,
    val writeModel: MarketWriteModel,
    val pModel: ProfileModel,
    val chatStorage: ChatStorage,
    val commandModel: UserCommandModel
) : BaseViewModel() {


    private var _connect: ArtDetailConnect? = null
    private val connect: ArtDetailConnect
        get() = requireNotNull(_connect)


    /**
     * 액티비티에 필요한 기능을 요청하는 connect 클래스
     */
    fun registerConnect(connect: ArtDetailConnect) {
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
     * 팔로우 한 작가인가?
     */
    val isFollowing = MutableStateFlow(false)

    /**
     * 작가 팔로워 수
     */
    val follower = MutableStateFlow(0)

    /**
     * 카테고리
     */
    val category = MutableStateFlow("")

    /**
     * 판매 완료되었나?
     */
    val complete = MutableStateFlow(false)

    val itemType = model.itemType

    /**
     * 작가 아이디
     */
    private var authorId : String? = "-1"

    /**
     * 상품 id
     */
    private var product = -1

    val imgAdapter = VpAdapter()


    /*********************************************************************
     * Click Event
     **********************************************************************/

    /**
     * Like 버튼을 눌렀을때 동작
     */
    fun toggleLike(){
        if (!checkLoginStatus()) return
        model.postProductLike(product,!isLike.value)
        isLike.value = !isLike.value
        likeCnt.value = likeCnt.value + if (isLike.value) 1 else -1
    }


    /**
     * Wish 버튼을 눌렀을때 동작
     */
    fun toggleWish(){
        if (!checkLoginStatus()) return
        model.postProductWish(product,!isWish.value)
        isWish.value = !isWish.value
        wishCnt.value = wishCnt.value + if (isWish.value) 1 else -1
    }

    /**
     * 더보기 버튼을 눌렀을때 동작
     */
    fun showMoreDialog() {
        if (!signModel.isUserLogin()) return

        if (authorId.isNullOrEmpty()) {
            uiModel.showToast(valueModel.getString(R.string.str_null_user))
            return
        }

        if (isMyProduct.value) {
            showEditDialog()
            return
        }

        showReportDialog()
    }

    /**
     * 채팅방 버튼을 눌렀을때 동작
     */
    fun onClickChat() {
        openChatPage()
    }

    /**
     * 가격 제안하기 버튼 눌렀을때 동작
     */
    fun onClickSuggest(){
        if (goToLoginIfNot()) return
        if (authorId.isNullOrEmpty()) {
            uiModel.showToast(valueModel.getString(R.string.str_null_user))
            return
        }
        model.setIdForSuggest(product)
        navigation.changePage(Page.ART_DETAIL_SUGGEST)
    }

    /**
     * 팔로우 버튼
     */
    fun onClickFollow(){
        if (!checkLoginStatus()) return
        if (authorId.isNullOrEmpty()) {
            uiModel.showToast(valueModel.getString(R.string.str_null_user))
            return
        }
        profileModel.followUser(isFollowing.value,authorId.toString())
        isFollowing.value = !isFollowing.value
        follower.value = follower.value + if (isFollowing.value) 1 else -1

    }

    /**
     * 작가 프로필 조회
     */
    fun onClickArtistProfile() {
        if (authorId.isNullOrEmpty()) {
            uiModel.showToast(valueModel.getString(R.string.str_null_user))
            return
        }
        useFlag(profileModel.profileLoadSuccess){
            profileModel.changeProfile(newUserId = authorId.toString())
            navigation.changePage(Page.PROFILE_OTHER)
        }
    }


    /***********************************************************************
     * DATA FLOW
     **********************************************************************/


    init {
        collectArt()

        useFlag(model.productDeleteSuccessFlag) {
            showToast(valueModel.getString(R.string.str_delete_product_success))
            navigation.revealHistory()
        }

        useFlag(model.productStatusUpdateFlag){
            complete.value = true
        }
    }

    /**
     * 작품 데이터를 구독한다. 필요한 데이터를 분리해서 state 를 저장한다.
     */
    private fun collectArt() {
        invokeStateFlow(model.art) { art ->
            art?.let {


                imgAdapter.imgs = moveLastItemToFirst(it.imgUrls?.toMutableList() ?: mutableListOf())
                imgAdapter.notifyDataSetChanged()

                authorId = it.authorId
                isLike.value = it.like
                likeCnt.value = it.likeCnt
                isWish.value = it.wish
                wishCnt.value = it.wishCnt
                product = it.productId
                isFollowing.value = it.isFollowing
                follower.value = it.authorFollowerCnt
                category.value = valueModel.getFirstCategory(it.category)
                isMyProduct.value = it.myPost
                complete.value = it.complete

            }

        }

    }

    private fun moveLastItemToFirst(list: MutableList<String>) : List<String> {
        if (list.size > 1) {
            val lastItem = list.last()
            for (i in list.size - 1 downTo 1) {
                list[i] = list[i - 1]
            }
            list[0] = lastItem
        }
        return list
    }


    /***********************************************************************
     * Dialog Show
     **********************************************************************/


    /**
     * 게시글 수정 다이알로그 열기
     */
    private fun showEditDialog() {
        connect.productEditDialog(
            edit = ::gotoEdit,
            delete = {
                showDeleteDialog()
            }
        )
    }

    private fun gotoEdit() {
        writeModel.startEdit(itemType.value, product, art.value!!)
        navigation.changePage(Page.NEW_POST_IMAGE)
    }

    /**
     * 작품 거래 상태 다이얼로그 열기
     */
    fun showOnSaleDialog()
    {
        if (!isMyProduct.value) return

        connect.showOnSaleDialog(
            offSale = {model.updateProductState(product,itemType.value == TYPE_MARKET_PRODUCT)},
            itemType = itemType.value
        )
    }

    /**
     * 작가 신고 / 차단 다아일로그 열기
     */
    private fun showReportDialog() {

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
    private fun showBlockDialog() {
        connect.artistBlockDialog {
            commandModel.blockUser(authorId.toString(), art.value!!.author)
            useFlag(commandModel.reportSuccessFlag) {
                uiModel.showToast("해당 유저를 차단했어요")
            }
        }

    }

    /**
     * 작품 삭제 다이알로그 열기
     */
    private fun showDeleteDialog() {
        connect.productDeleteDialog {
            model.deleteProduct(product)
        }
    }

    /**
     * 사용자 신고 페이지 가기
     */
    private fun goToReportPage() {

        if(goToLoginIfNot()) return

        art.value?.let { product ->
            commandModel.setReportInfo(product.authorId,product.author)
            navigation.changePage(Page.PROFILE_REPORT_TYPE)
        }
    }


    private fun openChatPage() {
        art.value ?: run {
            return
        }
        val product = art.value!!

        if(goToLoginIfNot()) return

        if (authorId.isNullOrEmpty()) {
            uiModel.showToast(valueModel.getString(R.string.str_null_user))
            return
        }

        if (isMyProduct.value) {
            val productId = product.productId
            val type = if (itemType.value == TYPE_MARKET_PRODUCT) "product" else "work"

            chatStorage.getChatRoomFromPost(
                productId,
                type
            )
            navigation.changePage(Page.CHAT_ROOM_FROM_POST)

            return
        }

        chatStorage.clearChatMsg()
        chatStorage.chatProductExistFlag.value = true
        model.makeNewChatroom()

        navigation.changePage(Page.CHAT_ROOM)

    }

    private fun checkLoginStatus() : Boolean{
        if (!signModel.isUserLogin()) {
            uiModel.showToast(valueModel.getString(R.string.str_need_login))
            return false
        }
        return true
    }

    private fun goToLoginIfNot() : Boolean{
        if (!signModel.isUserLogin()) {
            uiModel.gotoLogin()
            return true
        }
        return false
    }

}