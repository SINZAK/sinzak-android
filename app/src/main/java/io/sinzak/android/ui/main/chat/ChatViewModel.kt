package io.sinzak.android.ui.main.chat

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : BaseViewModel() {


    val chatRoomAdapter = ChatRoomAdapter()
}