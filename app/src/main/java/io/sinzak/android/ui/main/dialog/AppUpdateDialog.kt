package io.sinzak.android.ui.main.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogUpdateAppBinding
import io.sinzak.android.ui.base.BaseDialog

class AppUpdateDialog(
    context: Context,
    val updateApp : () -> Unit
) :
    BaseDialog<DialogUpdateAppBinding>(
        context,
        R.layout.dialog_update_app
    )
{
    override fun initBind(bind: DialogUpdateAppBinding) {

        bind.setGoToStore {
            updateApp()
            dismiss()
        }

        bind.setDismiss {
            dismiss()
        }
    }

}