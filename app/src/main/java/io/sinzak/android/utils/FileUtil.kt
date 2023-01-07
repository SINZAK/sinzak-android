package io.sinzak.android.utils

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseActivity

object FileUtil {




    fun pickFromGallery(
        activity : BaseActivity<*>,
        onSuccess : (List<Uri>)->Unit
    ){


        val SAMSUNG_GALLERY = "com.sec.android.gallery3d"
        val ANDROID_GALLERY = "com.android.gallery"
        val GOOGLE_PHOTO = "com.google.android.apps.photos"

        // 갤러리 권한





        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        val resolveInfoList: List<ResolveInfo?> =
            activity.packageManager.queryIntentActivities(intent, 0)
        for (i in resolveInfoList.indices) {
            if (resolveInfoList[i] != null) {
                val packageName = resolveInfoList[i]!!.activityInfo.packageName
                if(packageName in listOf(SAMSUNG_GALLERY, ANDROID_GALLERY, GOOGLE_PHOTO))
                {
                    intent.component =
                        ComponentName(packageName, resolveInfoList[i]!!.activityInfo.name)
                    break
                }

            }
        }


        activity.gotoActivityForResult(intent) { result ->
            LogInfo(javaClass.name,"${result?.clipData}")
            if (result?.clipData != null) {
                val list = mutableListOf<Uri>()
               for(i in 0 until result.clipData!!.itemCount){
                   list.add(result.clipData!!.getItemAt(i).uri)
               }
                onSuccess(list)
            }
            else if(result?.data != null){
                onSuccess(listOf(result.data!!))
            }
        }


    }
}