package io.sinzak.android.ui.main.dialog

import android.content.Context
import io.sinzak.android.R
import io.sinzak.android.databinding.DialogRequestPermissionBinding
import io.sinzak.android.ui.base.BaseDialog

class PermissionDialog(
    context: Context,
    val allowPermission : () -> Unit
) : BaseDialog<DialogRequestPermissionBinding>(
    context,
    R.layout.dialog_request_permission
    )
{
    override fun initBind(bind: DialogRequestPermissionBinding) {
        bind.setAllowPermission {
            allowPermission()
            dismiss()
        }
    }

}