package io.sinzak.android.ui.main.profile.follow

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_FOLLOW_PAGE
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.profile.Follow
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
    private val _page = MutableStateFlow(0)
    val page : StateFlow<Int> get() = _page

    val adapter = FollowAdapter {
        model.changeProfile(it)
        navigation.changePage(Page.PROFILE_OTHER)
    }.apply {
        model.followList.onEach {
            setFollows(it)
        }.launchIn(viewModelScope)
    }

    init {
        CoroutineScope(Dispatchers.Main).launch {
            subscribe()
        }
    }

    private fun subscribe()
    {
        invokeStateFlow(navigation.bundleInserted){
            navigation.getBundleData(this::class)?.apply {
                getPageType(this)
            }
        }
    }

    private fun getPageType(bundle: Bundle)
    {
        bundle.getInt(CODE_FOLLOW_PAGE).let {
            _page.value = it
        }
    }

    fun changePage(page : Int)
    {
        _page.value = page
        getFollowListRemote(page)
    }

    fun getCurrentName() = model.profile.value?.name

    fun getFollowListRemote() {
        model.getFollowList(page = _page.value)
    }
    fun getFollowListRemote(page: Int) {
        model.getFollowList(page = page)
    }
}