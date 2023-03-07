package io.sinzak.android.ui.main

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.main.dialog.AppUpdateDialog
import io.sinzak.android.ui.main.dialog.PermissionDialog
import javax.inject.Inject

@ActivityRetainedScoped
class MainConnect @Inject constructor() {

    private lateinit var context: Context

    private var dialog : Dialog? = null

    fun registerActivity(activity :BaseActivity<*>){
        context = activity
    }

    private fun isDialogOn() : Boolean{
        dialog?.let{
            if(it.isShowing) return true
        }
        return false
    }

    fun updateAppDialog()
    {
        if (isDialogOn())
            return
        dialog = AppUpdateDialog(context,::goStore).apply {
            show()
        }
    }

    fun permissionDialog(requestPermission : ()->Unit)
    {
        if(isDialogOn())
            return
        dialog = PermissionDialog(context,requestPermission).apply {
            show()
        }
    }

    private fun goStore(){
        val appPackageName = context.packageName

        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (exception: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }

/*    fun checkNeedUpdate(activity: BaseActivity<*>)
    {
        // AppUpdateManager 객체를 만듭니다.
        val appUpdateManager =

        // 현재 사용 가능한 업데이트 정보를 가져옵니다.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // 업데이트가 필요한 경우, 다이얼로그를 띄워서 사용자에게 업데이트 여부를 물어봅니다.

            }
        }

    }*/




}