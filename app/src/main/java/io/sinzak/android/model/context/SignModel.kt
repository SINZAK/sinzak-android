package io.sinzak.android.model.context

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBehavior
import io.sinzak.android.R
import io.sinzak.android.constants.*
import io.sinzak.android.enums.SDK
import io.sinzak.android.model.BaseModel
import io.sinzak.android.model.GlobalValueModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.SchoolData
import io.sinzak.android.remote.dataclass.request.login.*
import io.sinzak.android.remote.dataclass.response.login.*
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.App.Companion.NAVER_SDK_PREPARED
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import io.sinzak.android.system.social.NaverImpl
import io.sinzak.android.ui.login.LoginActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignModel @Inject constructor(
    val valueModel: GlobalValueModel
) : BaseModel() {

    @Inject lateinit var profile : ProfileModel


    private val _isLogin = MutableStateFlow(false)



    private var loginEmail : String = ""
    /**
     * 현재 로그인되어있는지 확인
     */
    val isLogin : StateFlow<Boolean> get() = _isLogin

    val needSignUp = MutableStateFlow(false)

    private var oAuthTokenTaken = ""
    private var socialOrigin = ""
    private var oAuthIdToken = ""

    private val _sdkSignSuccess = MutableStateFlow(false)
    val sdkSignSuccess : StateFlow<Boolean> get() = _sdkSignSuccess


    /**
     * 마케팅 수신 동의
     */
    var termMarketing = false

    private val _signFailed = MutableStateFlow(false)
    private val _errorString = MutableStateFlow("")

    val resignSuccessFlag = MutableStateFlow(false)


    /**************************************************************************************************************************
     * REGISTER ACTIVITY
     *********************************************************************************************************************************/

    private lateinit var loginIntentActivity : LoginActivity

    fun registerLoginActivity(activity: LoginActivity)
    {
        loginIntentActivity = activity
    }



    /**************************************************************************************************************************
     * SESSION CHECK
     *********************************************************************************************************************************/


    fun isUserLogin() : Boolean{
        return  isLogin.value
    }

    fun checkToken(){
        if(LEGACY_REISSUE_MODE)
            if(isLogin.value)
                return
        setIsLogin(false)
        CallImpl(
            API_REFRESH_TOKEN,
            this,
            TokenRequest(
                accessToken = prefs.accessToken,
                refreshToken = prefs.refreshToken
            )
        ).apply{
            remote.sendRequestApi(this)
        }

    }
    fun clearJoinInfo(){
        userDisplayName = ""
        interests = ""
        univ = null
        univEmail = ""
    }


    /**************************************************************************************************************************
     * SIGNUP INFORMATION
     *********************************************************************************************************************************/


    val univList : List<SchoolData> get() = valueModel.univMap.map {
        SchoolData(it.key, it.value)
    }


    /**
     * 유저 본명
     */
    private var username : String = "'"
    /**
     * 닉네임
     */
    private var userDisplayName : String = ""
    private fun setUsername(t : String){
        userDisplayName = t
    }
    private var interests : String = ""
    fun setInterests(i : String){
        interests = i
    }
    private var univ : SchoolData? = null
    fun setUniv(u : SchoolData){
        univ = u
    }
    private var univEmail = ""
    //todo : 이메일 인증


    var sdkType : SDK? = null


    fun getUnivAddress() : String{
        return univ!!.schoolDomain
    }

    fun getUniv() : SchoolData {
        return univ!!
    }


    fun join(){
        JoinRequest(
            categoryLike = interests,
            nickname = userDisplayName,
            term = termMarketing,
        ).apply{
            CallImpl(API_JOIN_ACCOUNT,this@SignModel,this).apply{
                remote.sendRequestApi(this)
            }
        }
    }

    val nameCheckSuccessFlag = MutableStateFlow(false)

    fun checkName(name : String)
    {
        setUsername(name)
        CallImpl(
            API_CHECK_NAME,
            this,
            paramStr0 = name
        ).apply {
            remote.sendRequestApi(this)
        }
    }



    private lateinit var naverCallback : NaverImpl

    private lateinit var loginKaKao : UserApiClient

    fun initSignStatus(){
        _signFailed.value = false
        _errorString.value = ""
        setIsLogin(false)
        needSignUp.value = false
        _sdkSignSuccess.value = false
    }




    /**************************************************************************************************************************
     * NAVER
     *********************************************************************************************************************************/



    private fun initNaverSdk(){
        if(!::naverCallback.isInitialized)
            naverCallback = NaverImpl(_sdkSignSuccess,_signFailed,_errorString)
    }




    /**
     * 네이버 네아로 로그인을 요청합니다.
     */
    fun loginViaNaver(){

        if(!NAVER_SDK_PREPARED){
            /**
             * 일부 빌드 환경에서 google crypto 문제로 네아로 initialize 가 안되는 경우가 생깁니다.
             */
            globalUi.showToast("네이버 로그인 사용 불가합니다.")
            return
        }

        initSignStatus()
        sdkType = SDK.Naver
        initNaverSdk()
        //todo : Login With Naver

        NaverIdLoginSDK.behavior = NidOAuthBehavior.DEFAULT
        loginIntentActivity.requestNaverLoginActivity(naverCallback)

    }

    /**
     * 네이버 로그인 성공하여, 토큰을 통해 네이버 유저 정보를 가져옵니다.
     */
    fun onSuccessNaverLogin()
    {
        NaverIdLoginSDK.getAccessToken()?.let{token->
            oAuthIdToken = ""
            oAuthTokenTaken = token
            socialOrigin = SDK.Naver.displayName
            CallImpl(API_EMAIL_GET_NAVER,
            this,
            paramStr0 = token).apply{
                remote.sendRequestApi(this)
            }
        }
    }


    /**************************************************************************************************************************
     * KAKAO
     *********************************************************************************************************************************/


    private fun initKakaoSdk(){
        if(!::loginKaKao .isInitialized)
            loginKaKao = UserApiClient()
    }




    /**
     * 카카오톡 로그인을 요청합니다.
     */
    fun loginViaKakao(){
        initSignStatus()
        initKakaoSdk()

        loginIntentActivity.requestKakaoLoginActivity(loginKaKao,::onLoginKakao)
    }

    /**
     * 토큰을 취득하여, 카카오 계정 정보 취득을 합니다.
     */
    private fun onLoginKakao(token : OAuthToken?, error : Throwable?)
    {
        error?.let{
            if(error is AuthError){
                if(error.statusCode == 302){
                    loginIntentActivity.requestKakaoLoginActivity(loginKaKao,::onLoginKakao, forceWeb = true)

                    return
                }
            }
            _errorString.value = it.toString()
            LogError(error)
            _signFailed.value = true
            return
        }
        token?.let{
            _sdkSignSuccess.value = true
            oAuthTokenTaken = token.accessToken
            oAuthIdToken = ""
            socialOrigin = SDK.Kakao.displayName
            LogInfo(javaClass.name,"카카오 로그인 성공 : $token")
            getKakaoEmail()

        }?:run{
            _signFailed.value = true
            _errorString.value = "토큰 존재하지 않음"
            LogError(javaClass.name,"토큰이 존재하지 않습니다.")
        }
    }

    private fun getKakaoEmail(){
        loginKaKao.me { user, error ->

            error?.let{
                LogError(error)

            }

            user?.kakaoAccount?.let{
                _sdkSignSuccess.value = true
                loginEmail = it.email.toString()
                username = it.name.toString()
                sdkType = SDK.Kakao
                loginToServer()
                return@me
            }

            _signFailed.value = true

        }

    }

    /**************************************************************************************************************************
     * GOOGLE
     *********************************************************************************************************************************/


    /**
     * 구글로 로그인합니다
     * 1. 모델 변수 초기화
     * 2. 연결된 계정으로 필요한 값 뽑아옴
     */
    fun loginViaGoogle(completedTask: Task<GoogleSignInAccount>){
        initSignStatus()
        handleGoogleSignInResult(completedTask)
    }

    /**
     * 1. 로그인 후 계정에서 아이디 토큰 && 인증 코드를 뽑아옵니다
     */
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account : GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            account.let {
                _sdkSignSuccess.value = true
                sdkType = SDK.Google
                val authCode = it.serverAuthCode.toString()
                loginEmail = account.email.toString()
                username = account.displayName.toString()

                oAuthIdToken = it.idToken.toString()
                socialOrigin = SDK.Google.displayName

                getGoogleAccessToken(authCode)
            }

        } catch (e: ApiException) {
            LogError(e)
        }
    }

    /**
     * 2. 받아온 인증코드로 구글에 엑세스토큰을 요청합니다
     */
    private fun getGoogleAccessToken(authCode : String)
    {
        val request = GoogleRequest(
            grant_type = GOOGLE_GRANT_TYPE,
            client_id = GOOGLE_CLIENT_ID,
            client_secret = GOOGLE_SECRET_ID,
            code = authCode
        )
        CallImpl(
            API_GET_GOOGLE_ACCESS_TOKEN,
            this,
            requestBody = request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 3. 서버로 뽑아온 아이디 토큰과 엑세스 토큰을 보내줍니다
     */
/*    private fun postGoogleOAuthToken(accessToken : String, idToken : String)
    {
        val request = OAuthRequest(
            accessToken = accessToken,
            idToken = idToken,
            origin = sdkType!!.name
        )
        CallImpl(
            API_POST_GOOGLE_OAUTH_TOKEN,
            this,
            requestBody = request
        ).apply {
            remote.sendRequestApi(this)
        }
    }*/


    /**
     * 로그인, 회원가입 성공시 가지고 있는 소셜 정보를 prefs 에 저장합니다.
     */
    private fun saveTokenToPrefs(origin : String){
        prefs.setString(CODE_OAUTH_ORIGIN, origin)
        prefs.setString(CODE_OAUTH_IDTOKEN, oAuthIdToken)
        prefs.setString(CODE_OAUTH_AUTHTOKEN, oAuthTokenTaken)
    }

    /**
     * 최초 refresh 실패 시, 가지고 있는 소셜 정보를 통해 재 로그인을 시도합니다.
     */
    private fun restoreSsoTokenFromPrefs(){
        prefs.accessToken = ""
        prefs.refreshToken = ""
        socialOrigin = prefs.getString(CODE_OAUTH_ORIGIN,"").toString()
        oAuthIdToken = prefs.getString(CODE_OAUTH_IDTOKEN,"").toString()
        oAuthTokenTaken = prefs.getString(CODE_OAUTH_AUTHTOKEN,"").toString()
        if(oAuthTokenTaken.isNotEmpty())
            loginToServer()
        else
            LogInfo(javaClass.name,"NO STORED LOGIN INFORMATION")
    }


    /**************************************************************************************************************************
     * REQUEST
     *********************************************************************************************************************************/


    private fun setIsLogin(status : Boolean){
        LogInfo(javaClass.name,"Login Status Updated : $status")
        _isLogin.value = status
        prefs.setBoolean(CODE_IS_LOGIN, status)
    }




    /**
     * 백엔드에 로그인을 요청합니다.
     */
    private fun loginToServer(){
        postOAuthToken(oAuthTokenTaken, socialOrigin, oAuthIdToken)
    }

    private fun loginToServerViaEmail(email: String){
        CallImpl(
            API_LOGIN_EMAIL,
            this,
            LoginEmailBody(
                email = email
            )
        ).apply{
            remote.sendRequestApi(this)
        }
    }


    fun logout(){
        postFcmToken("", prefs.getString(CODE_USER_ID, "").toString())
        prefs.accessToken = ""
        prefs.refreshToken = ""
        setIsLogin(false)
    }

    fun resign(){
        CallImpl(
            API_USER_RESIGN,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    fun postFcmToken(fcmToken : String, userId : String) {
        CallImpl(
            API_POST_FCM_TOKEN,
            this,
            paramStr0 = fcmToken,
            paramStr1 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }
    /**************************************************************************************************************************
     * RESPONSE
     *********************************************************************************************************************************/


    /**
     * 토큰 재발급 응답 처리
     */
    private fun onRefreshToken(response : Token)
    {
        if(response.accessToken.isNullOrEmpty()){
            restoreSsoTokenFromPrefs()
            return

        }

        setIsLogin(true)
        prefs.setString(ACCESS_TOKEN,response.accessToken)
        prefs.setString(REFRESH_TOKEN,response.refreshToken)
        profile.getProfile()
    }

    /**
     * 로그인 응답 처리
     */
    private fun onResponseLogin(response : LoginEmailResponse){
        if(response.success == false){
            checkEmail(loginEmail)
        }else{
            // login
            saveTokenToPrefs(response.origin.toString())
            setIsLogin(true)
        }
    }


    /**
     * 회원가입
     */
    private fun onResponseJoin(response: JoinResponse)
    {
        if(response.success == true){
            needSignUp.value = false
            saveTokenToPrefs(response.token!!.origin.toString())
            setIsLogin(true)
            prefs.setString(CODE_USER_NAME,userDisplayName)
        }else{
            //todo 회원가입 실패
            LogError(javaClass.name,"회원가입 실패 ${response.message}")
        }
    }


    /**
     * OAUTH TOKEN POST
     */
    private fun postOAuthToken(token: String, origin: String, idToken: String? = null){

        CallImpl(API_POST_OAUTH_TOKEN, this, paramStr0 = token, paramStr1 = origin, paramStr2 = idToken).apply{
            remote.sendRequestApi(this)
        }
    }

    private fun checkEmail(email: String){

        if(LEGACY_LOGIN_MODE){

            loginToServerViaEmail(email)
            return
        }
        LogInfo(javaClass.name,"Email Check : {$email}")
        if(email.isEmpty()){
            LogError(javaClass.name,"이메일을 불러오는데 실패했습니다.")
            return
        }

        CallImpl(API_CHECK_EMAIL, this, paramStr0 = email).apply{
            remote.sendRequestApi(this)
        }
    }

    private fun onResponseOAuth(response : OAuthGetResponse){

        response.data?.let {
            if (it.joined!!) {
                saveTokenToPrefs(it.origin.toString())
                setIsLogin(true)
                _sdkSignSuccess.value = false
                profile.getProfile()
                profile.isFirstLogin.value = true
            }
            else checkEmail(loginEmail)
        }
    }



    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_LOGIN_EMAIL ->{
                onResponseLogin(body as LoginEmailResponse)
            }

            API_REFRESH_TOKEN ->{
                onRefreshToken(body as Token)
            }

            API_EMAIL_GET_NAVER ->{
                if(body is NaverProfile)
                {
                    loginEmail = body.profile?.email.toString()

                    loginToServer()
                }

            }

            API_CHECK_NAME -> {
                nameCheckSuccessFlag.value = body.success!!
            }

            API_JOIN_ACCOUNT ->{
                onResponseJoin(body as JoinResponse)
            }

            API_POST_OAUTH_TOKEN ->{
                onResponseOAuth(body as OAuthGetResponse)
            }

            /**
             * 구글에서 엑세스 토큰 발급을 성공하면 서버로 보내줍니다
             */
            API_GET_GOOGLE_ACCESS_TOKEN -> {
                body as GoogleResponse
                _sdkSignSuccess.value = true
                oAuthTokenTaken = body.access_token
                postOAuthToken(body.access_token, socialOrigin,body.id_token)
            }


            API_POST_GOOGLE_OAUTH_TOKEN -> {
                body as OAuthGetResponse

                if (body.success == true && body.data != null)
                {
                    setIsLogin(true)
                    _sdkSignSuccess.value = false
                }
                else {
                    checkEmail(loginEmail)
                }
            }

            API_CHECK_EMAIL ->{
                if(body.success == true){
                    needSignUp.value = true
                }else{

                    globalUi.showToast(body.message?:valueModel.getString(R.string.str_checkemail_exist))
                }
            }

            API_USER_RESIGN -> {
                if (body.success == true) {
                    resignSuccessFlag.value = true
                    prefs.setString(CODE_FCM_TOKEN,"")
                    logout()
                }
            }

            API_POST_FCM_TOKEN -> {
                LogDebug(javaClass.name, body.success.toString())
            }

        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
        _errorString.value = msg.toString()
        _signFailed.value = true

        when (api)
        {
            API_REFRESH_TOKEN ->{

                restoreSsoTokenFromPrefs()

            }
            API_LOGIN_EMAIL ->{

            }

            API_GET_GOOGLE_ACCESS_TOKEN -> {
                LogDebug(javaClass.name,"구글 엑세스토큰 발급실패")
            }
        }
    }


    companion object{
        const val LEGACY_LOGIN_MODE = false
        const val LEGACY_REISSUE_MODE = true
    }

}