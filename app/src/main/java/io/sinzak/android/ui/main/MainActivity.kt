package io.sinzak.android.ui.main

import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityMainBinding
import io.sinzak.android.databinding.ViewMainBottomMenuBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Page.*
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.chat.ChatFragment
import io.sinzak.android.ui.main.home.HomeFragment
import io.sinzak.android.ui.main.home.more.HomeMoreFragment
import io.sinzak.android.ui.main.home.notification.NotificationFragment
import io.sinzak.android.ui.main.market.MarketFragment
import io.sinzak.android.ui.main.market.artdetail.ArtDetailFragment
import io.sinzak.android.ui.main.market.artdetail.suggest.SuggestFragment
import io.sinzak.android.ui.main.outsourcing.OutsourcingFragment
import io.sinzak.android.ui.main.postwrite.fragment.ArtInfoFragment
import io.sinzak.android.ui.main.postwrite.fragment.CategoryFragment
import io.sinzak.android.ui.main.postwrite.fragment.ImageFragment
import io.sinzak.android.ui.main.postwrite.fragment.SpecFragment
import io.sinzak.android.ui.main.profile.ProfileFragment
import io.sinzak.android.ui.main.profile.certification.CertificationFragment
import io.sinzak.android.ui.main.profile.certification.VerifyFragment
import io.sinzak.android.ui.main.profile.certification.WebmailFragment
import io.sinzak.android.ui.main.profile.edit.EditFragment
import io.sinzak.android.ui.main.profile.report.ReportSendFragment
import io.sinzak.android.ui.main.profile.report.ReportTypeFragment
import io.sinzak.android.ui.main.profile.sale_with_work.RequestFragment
import io.sinzak.android.ui.main.profile.sale_with_work.SaleFragment
import io.sinzak.android.ui.main.profile.sale_with_work.WorkFragment
import io.sinzak.android.ui.main.profile.scrap.ScrapFragment
import io.sinzak.android.ui.main.profile.setting.SettingFragment
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import io.sinzak.android.utils.RootViewDeferringInsetsCallback
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var signModel: SignModel

    @Inject
    lateinit var homeProductModel: HomeProductModel

    private val viewModel: MainViewModel by viewModels()

    private val bottomViewModel: MainBottomViewModel by viewModels()

    private val profileViewModel: ProfileViewModel by viewModels()

    private var fragment: BaseFragment? = null

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        lifecycleScope.launch {
            homeProductModel.recommendProducts.collect {
                LogDebug(javaClass.name, "수집수집수집")
            }

        }

        inflateBottomMenu()

        attachInsetsCallback()



        signModel.checkToken()
    }


    private fun attachInsetsCallback() {
        RootViewDeferringInsetsCallback(
            WindowInsetsCompat.Type.systemBars(),
            WindowInsetsCompat.Type.ime()
        ).apply {
            useBind {
                ViewCompat.setOnApplyWindowInsetsListener(root, this@apply)
                ViewCompat.setWindowInsetsAnimationCallback(root, this@apply)

            }
        }
    }


    private fun inflateBottomMenu() {


        useBind {
            DataBindingUtil.inflate<ViewMainBottomMenuBinding>(
                layoutInflater,
                R.layout.view_main_bottom_menu,
                null,
                false
            ).apply {
                activity = this@MainActivity
                viewmodel = bottomViewModel
                pViewmodel = profileViewModel
                lifecycleOwner = this@MainActivity
                flBottomMenu.addView(root)
            }
        }
    }

    override fun onBackPressed() {

        if(viewModel.netStatus.onRemotePending.value)
            return

        fragment?.run {
            this.navigateOnBackPressed()
        } ?: run {
            super.onBackPressed()
        }
    }


    fun inflateFragment(page: Page): Boolean {
        fragment =
            when (page) {
                HOME -> {
                    navigation.clearHistory()
                    HomeFragment()
                }
                HOME_MORE -> {
                    HomeMoreFragment()
                }
                MARKET -> {
                    navigation.clearHistory()
                    MarketFragment()
                }
                HOME_NOTIFICATION ->
                    NotificationFragment()
                PROFILE -> {
                    navigation.clearHistory()
                    ProfileFragment()
                }
                OUTSOURCING -> {
                    navigation.clearHistory()
                    OutsourcingFragment()
                }
                CHAT -> {
                    navigation.clearHistory()
                    ChatFragment()
                }

                PROFILE_SALE,
                PROFILE_WORK,
                PROFILE_EDIT,
                PROFILE_SETTING,
                PROFILE_CERTIFICATION,
                PROFILE_REPORT_TYPE,
                PROFILE_REPORT_SEND,
                PROFILE_SCRAP,
                PROFILE_REQUEST->
                    inflateProfileFragments(page)


                NEW_POST, NEW_POST_IMAGE, NEW_POST_INFO, NEW_POST_SPEC ->
                    inflateWriteFragments(page)

                PROFILE_WEBMAIL ->
                    WebmailFragment()
                PROFILE_VERIFY ->
                    VerifyFragment()
                ART_DETAIL ->
                    ArtDetailFragment()

                ART_DETAIL_SUGGEST ->
                    SuggestFragment()
            }

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fc_main, it).commit()
            viewModel.setBottomMenuVisibility(it.showBottomBar())
            LogDebug(javaClass.name, "BOTTOM VISIBLITY = ${it.showBottomBar()}")
        }


        return true
    }


    private fun inflateWriteFragments(page: Page): BaseFragment {
        return when (page) {
            NEW_POST -> CategoryFragment()
            NEW_POST_IMAGE -> ImageFragment()
            NEW_POST_SPEC -> SpecFragment()
            else -> ArtInfoFragment()
        }
    }


    private fun inflateProfileFragments(page: Page): BaseFragment {
        return when (page) {
            PROFILE_SALE ->
                SaleFragment()
            PROFILE_WORK ->
                WorkFragment()
            PROFILE_EDIT ->
                EditFragment()
            PROFILE_SETTING ->
                SettingFragment()
            PROFILE_CERTIFICATION ->
                CertificationFragment()
            PROFILE_REPORT_TYPE ->
                ReportTypeFragment()
            PROFILE_SCRAP ->
                ScrapFragment()
            PROFILE_REQUEST ->
                RequestFragment()
            else ->
                ReportSendFragment()
        }
    }


}