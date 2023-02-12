package io.sinzak.android.utils

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseActivity

object GoogleAccountUtil {

    fun loginGetAccount(
        activity : BaseActivity<*>,
        signInClient: GoogleSignInClient,
        onSuccess : (Task<GoogleSignInAccount>)->Unit
    ){
        val signInIntent : Intent = signInClient.signInIntent
        activity.gotoActivityForResult(signInIntent){ result ->

            if (result != null)
            {
                val completeTask = GoogleSignIn.getSignedInAccountFromIntent(result)
                onSuccess(completeTask)
            }
        }
    }

    fun signOut(
        signInClient: GoogleSignInClient
    ){
        signInClient.signOut()
            .addOnCompleteListener {
                LogDebug(javaClass.name,"로그아웃되었습니다")
            }
    }

    fun isLogin(
        context: Context
    ) : Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null
    }

}