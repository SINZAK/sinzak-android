package io.sinzak.android.ui.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.sinzak.android.constants.GOOGLE_CLIENT_ID
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.GoogleAccountUtil
import javax.inject.Inject

@ActivityScoped
class LoginConnect @Inject constructor(
    @ActivityContext val context: Context
) {

    private lateinit var activity : BaseActivity<*>

    fun registerActivityContext(a : BaseActivity<*>)
    {
        activity = a
    }

    /**
     * 구글 로그인 옵션
     */
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(GOOGLE_CLIENT_ID)
        .requestServerAuthCode(GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()

    /**
     * 구글 로그인 클라이언트
     */
    private val googleSignInClient = GoogleSignIn.getClient(context, gso)

    /**
     * 구글 로그인 후 계정정보 가져오기
     */
    fun getGoogleAccount(callback : (Task<GoogleSignInAccount>) -> Unit)
    {
        GoogleAccountUtil.loginGetAccount(activity,googleSignInClient){
            callback(it)
        }
    }

    /**
     * 구글 로그아웃
     */
    fun googleSignOut()
    {
        GoogleAccountUtil.signOut(googleSignInClient)
    }

    /**
     * 구글 로그인 중인가
     */
    fun isLoginViaGoogle() : Boolean {
        return GoogleAccountUtil.isLogin(context)
    }




}