package io.sinzak.android.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*

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


    fun getBitmapFile(context : Context, uri : Uri) : Bitmap{
        val path = getRealPath(context,uri)
        val bitmap = BitmapFactory.decodeFile(path)
        return Bitmap.createScaledBitmap(bitmap,256,256,true).apply{
            LogDebug(TAG,"Size : ${this.byteCount} ${this.allocationByteCount} ${this.rowBytes}")
        }
    }

    fun getRealPath(context : Context, uri : Uri) : String?{


        getRealPathAPI19(context, uri).apply{
            this?.apply{
                return this
            }


            getPath21(context, uri)?.apply{
                return this
            }
            val documentId = DocumentsContract.getDocumentId(uri)

            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val file = File("${context.cacheDir.absolutePath}/$documentId")
            if (inputStream != null) {
                writeFile(inputStream,file)
                return file.absolutePath
            }




            return null
        }


    }


    private fun getPath21(context : Context, uri : Uri) : String?
    {
        var fullPath: String? = null
        val column = MediaStore.Images.Media.DATA
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToNext()
            var documentId = cursor.getString(0)
            if (documentId == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column == cursor.getColumnName(i)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                documentId = documentId.substring(documentId.lastIndexOf(":") + 1)
                cursor.close()

                fullPath = processDocumentId(context, documentId)

            }
        }

        LogDebug(TAG, "fullPath : $fullPath")
        return fullPath
    }


    private fun processDocumentId(context : Context, documentId : String?) : String?
    {
        val projection = arrayOf("_data")
        var fullPath : String? = null
        var  cursor : Cursor? = null
        try {
            cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media._ID + " = ? ",
                arrayOf(documentId),
                null
            )
            if (cursor != null) {
                cursor.moveToNext()
                fullPath = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return fullPath

    }




    private fun getRealPathAPI19(context : Context, uri : Uri) : String?{
        if(DocumentsContract.isDocumentUri(context,uri))
        {

            val documentId = DocumentsContract.getDocumentId(uri)


            LogInfo(TAG,"$documentId ${uri.authority}")

            when(uri.authority)
            {
                TYPE_EXTERNAL ->{
                    documentId.split(':').apply{
                        if(size > 1)
                        {
                            return "${Environment.getExternalStorageDirectory().path}/${this[1]}"
                        }
                    }

                }


                TYPE_DOWNLOAD ->{

                    try {
                        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                        val file = File("${context.cacheDir.absolutePath}/$documentId")
                        if (inputStream != null) {
                            writeFile(inputStream,file)
                            return file.absolutePath
                        }


                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }


                }
                TYPE_MEDIA ->{
                    return getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "_id = ? ",arrayOf(documentId.split(':')[1])
                    )

                }
            }


        }
        if("content".equals(uri.scheme, ignoreCase = true))
        {
            if(uri.authority == TYPE_GOOGLEPHOTO)
                return uri.lastPathSegment
            return getDataColumn(context, uri,null,null)
        }


        if("file".equals(uri.scheme, ignoreCase = true))
        {
            return uri.path
        }



        return null
    }

    private fun writeFile(input: InputStream, file: File) {
        var out: OutputStream? = null
        try {
            out = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (input.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun getDataColumn(context : Context, uri : Uri, selection : String?, selectionArgs : Array<String>?) : String?
    {
        val projection = arrayOf("_data")
        context.contentResolver.query(uri,projection,selection,selectionArgs,null).use { cursor->
            if(cursor?.moveToFirst() == true)
            {
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"))
            }

        }
        return null
    }


    fun getMultipart(context: Context, partName : String, bitmap: Bitmap): MultipartBody.Part
    {
        val filesDir = context.applicationContext.filesDir
        val file = File(filesDir, filesDir.name + ".jpg")

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return MultipartBody.Part.createFormData(partName, file.name, file.asRequestBody("image/png".toMediaTypeOrNull()))

    }


    const val TYPE_EXTERNAL = "com.android.externalstorage.documents"
    const val TYPE_DOWNLOAD = "com.android.providers.downloads.documents"
    const val TYPE_MEDIA = "com.android.providers.media.documents"
    const val TYPE_GOOGLEPHOTO = "com.google.android.apps.photos.content"


    const val TAG = "[FILE_UTIL]"

}