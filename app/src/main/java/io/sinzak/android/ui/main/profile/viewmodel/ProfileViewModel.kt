package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_FCM_TOKEN
import io.sinzak.android.constants.CODE_USER_ID
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.ProfileConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val model: ProfileModel,
    private val commandModel: UserCommandModel
) : BaseViewModel() {

    private var _connect : ProfileConnect? = null
    private val connect : ProfileConnect get() = requireNotNull(_connect)

    /**
     * 액티비티에 필요한 기능을 요청하는 connect 클래스
     */
    fun registerConnect(connect: ProfileConnect)
    {
        _connect = connect
    }
    /**
     * 조회중인 프로필 프로필
     */
    val profile get() = model.profile

    /**
     * 내 프로필인가?
     */
    val isMyProfile = MutableStateFlow(false)

    /**
     * 소개
     */
    val styledIntro = MutableStateFlow("")

    /**
     * 팔로워 수
     */
    val follower = MutableStateFlow(0)

    /**
     * 팔로잉 수
     */
    val following = MutableStateFlow(0)

    /**
     * 팔로잉 중인지?
     */
    val isFollow = MutableStateFlow(false)


    /***********************************************************************
     * DATA FLOW
     **********************************************************************/
    init {
        collectProfile()
    }


    /**
     * 프로필 데이터를 구독하고 필요한 데이터를 분리해 state를 저장해요
     */
    private fun collectProfile()
    {
        invokeStateFlow(profile) {profile ->
            profile?.let {
                isMyProfile.value = it.myProfile
                styledIntro.value = it.introduction
                follower.value = it.followerNumber
                following.value = it.followingNumber
                isFollow.value = it.isFollow
            }
        }

    }

    fun postFcmToken()
    {
        useFlag(model.isFirstLogin){
            signModel.postFcmToken(
                fcmToken = prefs.getString(CODE_FCM_TOKEN,"").toString(),
                userId = prefs.getString(CODE_USER_ID,"").toString()
            )
        }
    }

    /********************************
     * REQUEST
     ********************************/

    fun getMyProfileRemote() = model.getProfile()

    fun getOtherProfileRemote() = model.getOtherProfile()

    /*********************************************************************
     * Click Event
     **********************************************************************/

    /**
     * 뒤로 가기 클릭시 프로필 히스토리 관리
     */
    private fun revealProfileHistory() = model.revealProfileHistory()

    /**
     * 채팅하기 클릭시 동작
     */
    fun onChatting(){
        if (goToLoginIfNot()) return
        navigation.changePage(Page.CHAT)
    }
    /**
     * 팔로우 버튼 클릭시 동작
     */
    fun toggleFollow()
    {

        if (!checkLoginStatus()) return

        model.followUser(isFollow.value,model.currentUserId.value)

        useFlag(model.followControlSuccessFlag) {
            isFollow.value = !isFollow.value
            follower.value = follower.value + if (isFollow.value) 1 else -1
        }

    }

    /**
     * 페이지 이동 관련된 동작
     */
    fun changePage(page: Page)
    {
        page.apply {
            navigation.changePage(this)
        }
    }

    /**
     * 팔로우/팔로워보기
     */
    fun goFollowPage(followerTap : Boolean){
        model.followPageTap.value = followerTap
        navigation.changePage(Page.PROFILE_FOLLOW)
    }

    /**
     * 스크랩 목록으로 이동
     */
    fun goToScrapList()
    {
        navigation.changePage(Page.PROFILE_SCRAP)
    }


    /**
     * 더보기 버튼을 눌렀을때 동작
     */
    fun showMoreDialog(){
        showReportDialog()
    }

    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
        revealProfileHistory()

    }
    /***********************************************************************
     * Dialog Show
     **********************************************************************/

    /**
     * 작가 신고 / 차단 다아일로그 열기
     */
    private fun showReportDialog(){

        uiModel.userReportDialog(
            profile.value!!.name,
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
        uiModel.userBlockDialog {
            commandModel.blockUser(profile.value!!.userId,profile.value!!.name)
            useFlag(commandModel.reportSuccessFlag){
                uiModel.showToast(valueModel.getString(R.string.str_block_user))
            }
        }
    }

    /**
     * 사용자 신고 페이지 가기
     */
    private fun goToReportPage(){

        if (goToLoginIfNot()) return

        profile.value?.let{profile->
            commandModel.setReportInfo(profile.userId,profile.name)
            navigation.changePage(Page.PROFILE_REPORT_TYPE)
        }
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