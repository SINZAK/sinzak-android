package io.sinzak.android.ui.main.market.artdetail.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistBlockBinding
import io.sinzak.android.ui.base.BaseDialog

class ArtistBlockDialog(
    context: Context,
    val blockArtist:() -> Unit
) :
    BaseDialog<DialogArtistBlockBinding>(
    context,
    R.layout.dialog_artist_block
) {
    override fun initBind(bind: DialogArtistBlockBinding) {
        bind.setBlockArtist {
            blockArtist()
            dismiss()
        }
        bind.setDismiss {
            dismiss()
        }
    }
}