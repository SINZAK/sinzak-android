package io.sinzak.android.ui.main.market.artdetail.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistReportBinding
import io.sinzak.android.databinding.DialogProductDeleteBinding
import io.sinzak.android.ui.base.BaseDialog

class ProductDeleteDialog(
    context: Context,
    val delete : ()->Unit
) :
    BaseDialog<DialogProductDeleteBinding>(
        context,
        R.layout.dialog_artist_report
    ) {

    override fun initBind(bind: DialogProductDeleteBinding) {
        bind.setDelete {
            delete()
            dismiss()
        }
        bind.setDismiss {
            dismiss()
        }
    }

}