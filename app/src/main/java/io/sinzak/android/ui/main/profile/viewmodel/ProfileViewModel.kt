package io.sinzak.android.ui.main.profile.viewmodel

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.constants.CODE_USER_REPORT_NAME
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.ProfileConnect
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val model: ProfileModel
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

    fun onResumeFragment()
    {
        if(!signModel.isUserLogin()) {
            uiModel.gotoLogin()
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
     * 팔로우 버튼 클릭시 동작
     */
    fun toggleFollow()
    {
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
     * 스크랩 목록으로 이동
     */
    fun goToScrapList()
    {
        model.getWishList()
        navigation.changePage(Page.PROFILE_SCRAP)
    }


    /**
     * 더보기 버튼을 눌렀을때 동작
     */
    fun showMoreDialog(){
        if (!signModel.isUserLogin()) return
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

        connect.userReportDialog(
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
        connect.userBlockDialog {
            //block artist
        }
    }

    /**
     * 사용자 신고 페이지 가기
     */
    private fun goToReportPage(){
        profile.value?.let{profile->
            Bundle().apply{
                putString(CODE_USER_REPORT_NAME, profile.name)
                putString(CODE_USER_REPORT_ID, profile.userId)
                navigation.putBundleData(ReportSendViewModel::class,this)
            }
            navigation.changePage(Page.PROFILE_REPORT_TYPE)
        }
    }

}