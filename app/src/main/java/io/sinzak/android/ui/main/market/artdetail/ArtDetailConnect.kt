package io.sinzak.android.ui.main.market.artdetail

import android.app.Dialog
import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistReportDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductDeleteDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductEditDialog
import javax.inject.Inject
import javax.inject.Singleton


@ActivityScoped
class ArtDetailConnect @Inject constructor(@ActivityContext val context: Context) {




    private var dialog: Dialog? = null

    private fun isDialogOn() : Boolean{
        dialog?.let{
            if(it.isShowing) return true
        }
        return false
    }

    fun artistReportDialog(
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

    fun artistBlockDialog(
        onBlock : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ArtistBlockDialog(context, onBlock).apply{
            show()
        }
    }

    fun productEditDialog(
        edit: () -> Unit,
        delete: () -> Unit
    ) {
        if(isDialogOn())
            return

        dialog = ProductEditDialog(context,edit, delete)
            .apply{
                show()
            }
    }

    fun productDeleteDialog(
        delete : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ProductDeleteDialog(context, delete).apply{
            show()
        }
    }



}