package io.sinzak.android.ui.main.chat

import android.app.Dialog
import android.content.Context
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.ui.main.MainActivity
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import javax.inject.Inject

@ActivityRetainedScoped
class ChatConnect @Inject constructor() {


    private lateinit var context: Context
    fun registerActivity(activity: MainActivity){
        context = activity
    }

    private var dialog: Dialog? = null


    private fun isDialogOn() : Boolean{
        dialog?.let{
            if(it.isShowing) return true
        }
        return false
    }

    fun showChatDialog(
        blockArtist:() -> Unit,
        reportArtist:() -> Unit,
        leaveChatroom: ()->Unit
    ){

        if(isDialogOn())
            return

        dialog =
        ChatroomDialog(
            context,
            blockArtist,
            reportArtist,
            leaveChatroom
        ).apply {
            show()
        }
    }

    fun showOnSaleDialog(
        updateSaleStatus: (Boolean) -> Unit
    ){
        if(isDialogOn())
            return

        dialog =
        ChatroomSaleDialog(
            context,
            updateSaleStatus
        ).apply {
            show()
        }

    }

    fun userBlockDialog(
        onBlock : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ArtistBlockDialog(context, onBlock).apply{
            show()
        }
    }


}