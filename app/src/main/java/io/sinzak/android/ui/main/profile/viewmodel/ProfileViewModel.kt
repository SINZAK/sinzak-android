package io.sinzak.android.ui.main.profile.viewmodel

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_ID
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.constants.CODE_USER_REPORT_NAME
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.ProfileConnect
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
     * 내 프로필
     */
    val profile get() = model.profile

    /**
     * 내 프로필인가?
     */
    val isMyProfile = MutableStateFlow(false)

    /**
     * 프로필 이미지 url
     */
    val profileImg = MutableStateFlow("")

    /**
     * 유저 이름
     */
    val name = MutableStateFlow("")

    /**
     * 학교 인증 받았는지?
     */
    val isCertify = MutableStateFlow(false)

    /**
     * 학교 이름
     */
    val university = MutableStateFlow("")

    /**
     * 인증 작가인지?
     */
    val isVerify = MutableStateFlow(false)

    /**
     * 팔로워 수
     */
    val follower = MutableStateFlow(0)

    /**
     * 팔로잉 수
     */
    val following = MutableStateFlow(0)

    /**
     * 소개 텍스트
     */
    val introduction = MutableStateFlow("")

    /**
     * 팔로잉 중인지?
     */
    val isFollow = MutableStateFlow(false)

    /**
     * 유저 아이디
     */
    val userId = MutableStateFlow("-1")

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
                userId.value = it.userId
                isMyProfile.value = it.myProfile

                profileImg.value = it.imageUrl
                name.value = it.name
                isCertify.value = it.cert_uni
                university.value = it.univ
//                isVerify.value = it.isVerify
                follower.value = it.followerNumber
                following.value = it.followingNumber
                introduction.value = it.introduction
                isFollow.value = it.ifFollow

            }
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
     * 팔로우 버튼 클릭시 동작
     */
    fun toggleFollow()
    {
        model.followUser(userId.value,isFollow.value)
        isFollow.value = !isFollow.value
        follower.value = follower.value + if (isFollow.value) 1 else -1
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
     * 더보기 버튼을 눌렀을때 동작
     */
    fun showMoreDialog(){
        showReportDialog()
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