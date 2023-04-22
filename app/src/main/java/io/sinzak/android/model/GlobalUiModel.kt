package io.sinzak.android.model

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.TYPE_MARKET_PRODUCT
import io.sinzak.android.constants.TYPE_MARKET_WORK
import io.sinzak.android.enums.Page
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.login.LoginActivity
import io.sinzak.android.ui.main.chat.ChatroomDialog
import io.sinzak.android.ui.main.chat.ChatroomSaleDialog
import io.sinzak.android.ui.main.dialog.AppUpdateDialog
import io.sinzak.android.ui.main.dialog.PermissionDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistReportDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductDeleteDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ProductEditDialog
import io.sinzak.android.utils.FileUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalUiModel @Inject constructor(
    @ApplicationContext val context : Context,
    val navigation: Navigation
) {
    private var activity : BaseActivity<*>? = null
    private var _context : Context? = null


    fun registerActivity(_activity : BaseActivity<*>, context: Context){
        activity = _activity
        _context = context
    }

    fun getActivity() : AppCompatActivity?{
        return activity
    }


    fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()

    }

    fun openProductDetail(){

        navigation.changePage(Page.ART_DETAIL)

    }

    /***********************************************************************
     * Dialog Builder
     **********************************************************************/

    private var dialog: Dialog? = null

    private fun isDialogOn() : Boolean{
        dialog?.let{
            if(it.isShowing) return true
        }
        return false
    }

    /**
     * 유저 신고 다이얼로그
     */
    fun userReportDialog(
        artist : String,
        onReport: () -> Unit,
        onBlock: () -> Unit

    ) {
        if(isDialogOn())
            return

        dialog =
            ArtistReportDialog(
                _context!!,
                artist,
                onReport,
                onBlock


            ).apply {
                show()
            }
    }

    /**
     * 유저 차단 다이얼로그
     */
    fun userBlockDialog(
        onBlock : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ArtistBlockDialog(_context!!, onBlock).apply{
            show()
        }
    }

    /**
     * 작품 편집 다이얼로그
     */
    fun productEditDialog(
        edit: () -> Unit,
        delete: () -> Unit
    ) {
        if(isDialogOn())
            return

        dialog = ProductEditDialog(_context!!,edit, delete)
            .apply{
                show()
            }
    }

    /**
     * 작품 삭제 다이얼로그
     */
    fun productDeleteDialog(
        delete : ()->Unit
    ){
        if(isDialogOn())
            return

        dialog = ProductDeleteDialog(_context!!, delete).apply{
            show()
        }
    }

    /**
     * 거래 상태 변경 다이얼로그
     */
    fun showOnSaleDialog(
        offSale : () -> Unit,
        itemType : Int
    ){
        if(isDialogOn())
            return

        dialog =
            ChatroomSaleDialog(
                _context!!,
                offSale,
                itemType

            ).apply {
                show()
            }

    }

    /**
     * 거래 상태 변경 다이얼로그 (채팅방)
     */
    fun showOnSaleDialog(
        offSale : () -> Unit,
        isProduct : Boolean
    ){
        if(isDialogOn())
            return

        val itemType = if (isProduct) TYPE_MARKET_PRODUCT else TYPE_MARKET_WORK

        dialog =
            ChatroomSaleDialog(
                _context!!,
                offSale,
                itemType

            ).apply {
                show()
            }

    }

    /**
     * 채팅방 다이얼로그
     */
    fun showChatDialog(
        blockArtist:() -> Unit,
        reportArtist:() -> Unit,
        leaveChatroom: ()->Unit
    ){

        if(isDialogOn())
            return

        dialog =
            ChatroomDialog(
                _context!!,
                blockArtist,
                reportArtist,
                leaveChatroom
            ).apply {
                show()
            }
    }

    /**
     * 앱 권한 다이얼로그
     */
    fun permissionDialog(requestPermission : ()->Unit)
    {
        if(isDialogOn())
            return
        dialog = PermissionDialog(_context!!,requestPermission).apply {
            show()
        }
    }

    /**
     * 앱 업데이트 다이얼로그
     */
    fun updateAppDialog()
    {
        if (isDialogOn())
            return
        dialog = AppUpdateDialog(context,::goStore).apply {
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




    fun gotoLogin(){
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
    }


    fun loadImage(callback : (List<Uri>)->Unit){
        activity?.let{
            FileUtil.pickFromGallery(it,{
                callback(it)
            },true)
        }

    }

}