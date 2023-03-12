package io.sinzak.android.ui.main.chat

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistBlockBinding
import io.sinzak.android.databinding.DialogChatroomBinding
import io.sinzak.android.databinding.DialogProductOnsaleBinding
import io.sinzak.android.ui.base.BaseDialog

class ChatroomSaleDialog(
    context: Context,
    val offSale : () -> Unit,
    val itemType : Int
) :
    BaseDialog<DialogProductOnsaleBinding>(
    context,
    R.layout.dialog_product_onsale
) {
    override fun initBind(bind: DialogProductOnsaleBinding) {

        bind.itemType = itemType

        bind.setOffSale {
            dismiss()
            offSale()
        }

        bind.setDismiss {
            dismiss()
        }



    }
}