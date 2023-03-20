package io.sinzak.android.ui.main.profile.follow

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.FOLLOWER_TAP
import io.sinzak.android.constants.FOLLOWING_TAP
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.follow.adapter.FollowAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    val model: ProfileModel
) : BaseViewModel() {

    val profile get() = model.profile

    val tap : StateFlow<Boolean> get() = model.followPageTap

    val adapter = FollowAdapter(::onClickItem)


    init {

        setAdapter()
    }

    /*
     * 어뎁터를 세팅합니다
     */
    private fun setAdapter()
    {
        adapter.apply {
            model.followList.onEach {
                setFollows(it)
            }.launchIn(viewModelScope)
        }
    }

    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 상단 탭을 누릅니다
     */
    fun changeTap(t : Boolean)
    {
        model.getFollowList(t)
    }

    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }

    /**
     * 리스트 중 하나를 누릅니다
     */
    private fun onClickItem(userId : String)
    {
        model.changeProfile(userId)
        navigation.changePage(Page.PROFILE_OTHER)
    }

    companion object {
        const val FOLLOWER = FOLLOWER_TAP
        const val FOLLOWING = FOLLOWING_TAP
    }
}