package io.sinzak.android.ui.main.profile

import android.app.Dialog
import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistReportDialog
import javax.inject.Inject

@ActivityScoped
class ProfileConnect @Inject constructor(@ActivityContext val context: Context)  {


    private var dialog: Dialog? = null


    private fun isDialogOn() : Boolean{
        dialog?.let{
            if(it.isShowing) return true
        }
        return false
    }

    fun userReportDialog(
        artist : String,
        onReport: () -> Unit,
        onBlock: () -> Unit

    ) {
        if(isDialogOn())
            return

        dialog =
            ArtistReportDialog(
                context,
                artist,
                onReport,
                onBlock


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