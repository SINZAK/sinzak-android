package io.sinzak.android.ui.main

import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityMainBinding
import io.sinzak.android.databinding.ViewMainBottomMenuBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Page.*
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.HomeFragment
import io.sinzak.android.ui.main.home.notification.NotificationFragment
import io.sinzak.android.ui.main.market.MarketFragment
import io.sinzak.android.ui.main.profile.ProfileFragment
import io.sinzak.android.ui.main.profile.sale_with_work.SaleFragment
import io.sinzak.android.ui.main.profile.sale_with_work.WorkFragment
import io.sinzak.android.utils.RootViewDeferringInsetsCallback
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)
{

    @Inject
    lateinit var navigation: Navigation

    private val viewModel : MainViewModel by viewModels()

    private val bottomViewModel : MainBottomViewModel by viewModels()

    private var fragment : BaseFragment? = null

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        inflateBottomMenu()

        attachInsetsCallback()
    }


    private fun attachInsetsCallback(){
        RootViewDeferringInsetsCallback(
            WindowInsetsCompat.Type.systemBars(),
            WindowInsetsCompat.Type.ime()
        ).apply{
            useBind {
                ViewCompat.setOnApplyWindowInsetsListener(root,this@apply)
                ViewCompat.setWindowInsetsAnimationCallback(root,this@apply)

            }
        }
    }


    private fun inflateBottomMenu(){


        useBind {
            DataBindingUtil.inflate<ViewMainBottomMenuBinding>(layoutInflater,R.layout.view_main_bottom_menu,null,false).apply{
                viewmodel = bottomViewModel
                lifecycleOwner = this@MainActivity
                flBottomMenu.addView(root)
            }
        }
    }

    override fun onBackPressed() {
        fragment?.run {
            this.navigateOnBackPressed()
        }?:run{
            super.onBackPressed()
        }
    }



    fun inflateFragment(page: Page) : Boolean
    {

        fragment =
        when(page)
        {
            HOME -> {
                navigation.clearHistory()
                HomeFragment()
            }
            MARKET ->{
                navigation.clearHistory()
                MarketFragment()
            }
            HOME_NOTIFICATION ->
                NotificationFragment()
            PROFILE -> {
                navigation.clearHistory()
                ProfileFragment()
            }
            PROFILE_SALE ->
                SaleFragment()
            PROFILE_WORK ->
                WorkFragment()
        }

        fragment?.let{
            supportFragmentManager.beginTransaction().replace(R.id.fc_main,it).commit()
            viewModel.setBottomMenuVisibility(it.showBottomBar())
            LogDebug(javaClass.name,"BOTTOM VISIBLITY = ${it.showBottomBar()}")
        }


        return true
    }




}