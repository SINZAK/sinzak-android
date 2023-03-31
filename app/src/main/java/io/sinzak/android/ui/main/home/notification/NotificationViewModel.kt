package io.sinzak.android.ui.main.home.notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.notification.NotificationModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.notification.adapter.NotificationAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    val model : NotificationModel,
    val pModel : ProfileModel
) : BaseViewModel() {
/*
    private val _page = MutableStateFlow(0)
    val page : StateFlow<Int> get() = _page

    fun changeNotificationList(page : Int)
    {
        _page.value = page
    }
*/

    val adapter = NotificationAdapter(::onClickItem)

    fun getNotificationList() = model.getRemoteNotificationList()

    init {
        setAdapter()
    }

    private fun setAdapter()
    {
        adapter.apply {
            model.notificationList.onEach {
                setNotifications(it)
            }.launchIn(viewModelScope)
        }
    }

    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 리스트 중 하나를 누릅니다
     */
    private fun onClickItem(userId : String)
    {
        pModel.changeProfile(userId)
        navigation.changePage(Page.PROFILE_OTHER)

    }

    fun onBackPressed(){
        navigation.revealHistory()
    }
}