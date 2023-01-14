package io.sinzak.android.ui.main.market.artdetail.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistBlockBinding
import io.sinzak.android.databinding.DialogProductDetailMoreBinding
import io.sinzak.android.ui.base.BaseDialog

class ProductEditDialog(
    context: Context,
    val edit:() -> Unit,
    val delete : () -> Unit
) :
    BaseDialog<DialogProductDetailMoreBinding>(
    context,
    R.layout.dialog_artist_block
) {
    override fun initBind(bind: DialogProductDetailMoreBinding) {
        bind.setEdit {
            edit()
            dismiss()
        }
        bind.setDelete {
            delete()
        }
        bind.setDismiss {
            dismiss()
        }
    }
}