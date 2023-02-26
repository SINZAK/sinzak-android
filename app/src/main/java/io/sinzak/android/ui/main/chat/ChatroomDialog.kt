package io.sinzak.android.ui.main.chat

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistBlockBinding
import io.sinzak.android.databinding.DialogChatroomBinding
import io.sinzak.android.ui.base.BaseDialog

class ChatroomDialog(
    context: Context,
    val blockArtist:() -> Unit,
    val reportArtist:() -> Unit,
    val leaveChatroom: ()->Unit
) :
    BaseDialog<DialogChatroomBinding>(
    context,
    R.layout.dialog_chatroom
) {
    override fun initBind(bind: DialogChatroomBinding) {
        bind.setBlockArtist {
            blockArtist()
            dismiss()
        }
        bind.setDismiss {
            dismiss()
        }
        bind.setLeaveChatroom {
            leaveChatroom()
            dismiss()
        }
        bind.setReportArtist {
            reportArtist()
            dismiss()
        }

    }
}