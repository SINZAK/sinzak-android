package io.sinzak.android.ui.base

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<T : ViewDataBinding>(context : Context, val layout : Int) : Dialog(context) {
    final override fun show(){
        val bind = DataBindingUtil.inflate<T>(layoutInflater,layout,null,false)

        initBind(bind)
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(bind.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.BOTTOM)
        super.show()
        val param = window?.attributes
        val dp = Resources.getSystem().displayMetrics.density
        param?.width = (Resources.getSystem().configuration.screenWidthDp*dp - 16*dp).toInt()
        window?.attributes = param
    }

    abstract fun initBind(bind : T)


}