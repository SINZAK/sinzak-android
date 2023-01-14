package io.sinzak.android.ui.main.market.artdetail.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogArtistReportBinding
import io.sinzak.android.ui.base.BaseDialog

class ArtistReportDialog(
    context: Context,
    val artist: String = "",
    val reportArtist: () -> Unit,
    val blockArtist: () -> Unit
) :
    BaseDialog<DialogArtistReportBinding>(
        context,
        R.layout.dialog_artist_report
    ) {

    override fun initBind(bind: DialogArtistReportBinding) {
        bind.artist = artist
        bind.setReportArtist {
            reportArtist()
            dismiss()
        }
        bind.setBlockArtist {
            blockArtist()
            dismiss()
        }
        bind.setDismiss {
            dismiss()
        }
    }

}