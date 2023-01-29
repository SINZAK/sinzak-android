package io.sinzak.android.ui.main.chat.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatroomFormViewModel @Inject constructor(
    private val storage: ChatStorage
): BaseViewModel() {


    fun sendTypedMsg(cs: String){

    }
}