package io.sinzak.android.constants

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

const val KAKAO_NATIVE = "f4e54dad18c8af8a67dccb0176283616"
const val KAKAO_REST_API = "3201538a34f65dfa0fb2e96b0d268ca7"
const val KAKAO_ADMIN = "64319ad26dc57870c5922a3f0ef36acf"


fun getHashKey(context : Context) {
    var packageInfo: PackageInfo? = null
    try {
        packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    if (packageInfo == null) LogError("KeyHash", "KeyHash:null")
    for (signature in packageInfo!!.signatures) {
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            //Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            LogInfo("KeyHash", Base64.getEncoder().encodeToString(md.digest()))
        } catch (e: NoSuchAlgorithmException) {
            LogInfo("KeyHash", "Unable to get MessageDigest. signature=$signature")

        }
    }

}