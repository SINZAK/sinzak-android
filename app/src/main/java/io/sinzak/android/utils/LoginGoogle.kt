package io.sinzak.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.sinzak.android.constants.GOOGLE_CLIENT_ID
import io.sinzak.android.model.GlobalUiModel
import io.sinzak.android.system.LogError
import javax.inject.Inject

class LoginGoogle(context: Context) {

    @Inject
    lateinit var globalUiModel: GlobalUiModel

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(GOOGLE_CLIENT_ID)
        .requestServerAuthCode(GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(context, gso)


    fun signIn(activity: Activity) {
        val signInIntent: Intent = googleSignInClient.signInIntent

        activity.startActivityForResult(signInIntent, GOOGLE_lOGIN_CODE)

    }

    fun signOut(context: Context) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                globalUiModel.showToast("로그아웃되었습니다")
            }
    }

    fun isLogin(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return if (account == null) false else (true)
    }

    companion object {
        const val GOOGLE_lOGIN_CODE = 1000
    }
}