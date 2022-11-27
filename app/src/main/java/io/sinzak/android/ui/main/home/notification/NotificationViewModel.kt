package io.sinzak.android.ui.main.home.notification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.notification.adapter.NotificationAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor() : BaseViewModel() {
    private val _page = MutableStateFlow(0)
    val page : StateFlow<Int> get() = _page

    fun changeNotificationList(page : Int)
    {
        _page.value = page
    }

    val adapter = NotificationAdapter()
}