package io.sinzak.android.ui.main.profile.follow

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.profile.Follow
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.follow.adapter.FollowAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    val model: ProfileModel
) : BaseViewModel() {
    private val _page = MutableStateFlow(0)
    val page : StateFlow<Int> get() = _page

    val adapter = FollowAdapter {
        navigation.changePage(Page.PROFILE_OTHER)
    }.apply {
        getFollowList(_page.value,this)
    }

    fun changePage(page : Int)
    {
        _page.value = page
    }

    private fun getFollowList(page: Int, adapter: FollowAdapter){
        if (page == 0){
            model.followerList.onEach {
                adapter.setFollows(it)
            }
        }
        else {
            model.followingList.onEach {
                adapter.setFollows(it)
            }
        }
    }

    fun getFollowListRemote() {
        if (_page.value == 0){
            model.getFollowerList(userId = model.profile.value!!.userId)
        }
        else {
            model.getFollowingList(userId = model.profile.value!!.userId)
        }
    }
}