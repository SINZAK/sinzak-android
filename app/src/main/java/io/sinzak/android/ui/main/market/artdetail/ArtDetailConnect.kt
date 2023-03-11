package io.sinzak.android.ui.main.market.artdetail

import android.app.Dialog
import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import io.sinzak.android.enums.Page
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.main.chat.ChatroomSaleDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistReportDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductDeleteDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductEditDialog
import javax.inject.Inject
import javax.inject.Singleton


@ActivityScoped
class ArtDetailConnect @Inject constructor(@ActivityContext val context: Context,
val navigation: Navigation) {




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

    fun showOnSaleDialog(
        tradingState: () -> Unit,
        saleState : () -> Unit,
        itemType : Int
    ){
        if(isDialogOn())
            return

        dialog =
            ChatroomSaleDialog(
                context,
                tradingState,
                saleState,
                itemType

            ).apply {
                show()
            }

    }



}