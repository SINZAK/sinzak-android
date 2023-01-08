package io.sinzak.android.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.LogDebug
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 주의 : Fragment 의 기본 생성자를 만질 경우 supportfragmentmanager 의 자원관리에 충돌날 수 있으므로,
 *  생성자로 데이터를 넘기는 행위를 삼가하도록 합니다.
 */
abstract class BaseFragment : Fragment(){


    @Inject
    lateinit var navigator : Navigation

    @Inject
    lateinit var ime : SoftKeyModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getFragmentRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogDebug(javaClass.name,"FRAGMENT VIEW CREATED")
        onFragmentCreated()
    }

    /**
     * DataBinding 으로 inflate 한 fragment 의 rootView 를 넘겨주세요
     */
    abstract fun getFragmentRoot() : View


    /**
     * Fragment 가 생성되었습니다.
     */
    abstract fun onFragmentCreated()

    /**
     * MainActivity 에서 Bottom Menu 가 보일지에 대한 설정
     */
    abstract fun showBottomBar() : Boolean

    /**
     * OnBackPressed 에서 이동할 뷰
     */
    abstract fun navigateOnBackPressed()



    fun showToast(msg : String)
    {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }



    fun <T> invokeStateFlow(state : StateFlow<T>, collect : (T)->Unit)
    {
        lifecycleScope.launch {
            state.collect{
                collect(it)
            }
        }
    }

    fun invokeBooleanFlow(state : StateFlow<Boolean>, onFalse : ()->Unit = {}, onTrue : ()->Unit)
    {
        invokeStateFlow(state){
            if(it)
                onTrue()
            else
                onFalse()
        }
    }



}