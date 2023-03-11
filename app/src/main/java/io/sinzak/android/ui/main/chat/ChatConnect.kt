package io.sinzak.android.ui.main.chat

import android.app.Dialog
import android.content.Context
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.constants.TYPE_MARKET_PRODUCT
import io.sinzak.android.constants.TYPE_MARKET_WORK
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


    fun userBlockDialog(
        onBlock : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ArtistBlockDialog(context, onBlock).apply{
            show()
        }
    }

    fun showOnSaleDialog(
        offSale : () -> Unit,
        isProduct : Boolean
    ){
        if(isDialogOn())
            return

        val itemType = if (isProduct) TYPE_MARKET_PRODUCT else TYPE_MARKET_WORK

        dialog =
            ChatroomSaleDialog(
                context,
                offSale,
                itemType

            ).apply {
                show()
            }

    }


}