package io.sinzak.android.ui.main.profile.follow

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_FOLLOW_PAGE
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.follow.adapter.FollowAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    val model: ProfileModel
) : BaseViewModel() {

    val profile get() = model.profile

    private val _tap = MutableStateFlow(FOLLOWER)
    val tap : StateFlow<Boolean> get() = _tap

    val adapter = FollowAdapter(::onClickItem)


    init {
        setAdaptData()
    }

    /*
     * 어뎁터에 데이터를 세팅합니다
     */
    private fun setAdaptData()
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
        _tap.value = t
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
        const val FOLLOWER = true
        const val FOLLOWING = false
    }
}