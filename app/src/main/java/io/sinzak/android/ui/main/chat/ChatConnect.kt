package io.sinzak.android.ui.main.chat

import android.content.Context
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.ui.main.MainActivity
import javax.inject.Inject

@ActivityRetainedScoped
class ChatConnect @Inject constructor() {


    private lateinit var context: Context
    fun registerActivity(activity: MainActivity){
        context = activity
    }

    fun showChatDialog(
        blockArtist:() -> Unit,
        reportArtist:() -> Unit,
        leaveChatroom: ()->Unit
    ){
        ChatroomDialog(
            context,
            blockArtist,
            reportArtist,
            leaveChatroom
        ).show()
    }

    fun showOnSaleDialog(
        updateSaleStatus: (Boolean) -> Unit
    ){
        ChatroomSaleDialog(
            context,
            updateSaleStatus
        ).show()

    }


}