package io.sinzak.android.system

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sinzak.android.constants.ACCESS_TOKEN
import io.sinzak.android.constants.REFRESH_TOKEN

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERNCE, 0)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun getStringArray(key: String): MutableList<String> {
        val gson = Gson()
        val arrayType = object : TypeToken<MutableList<String>>() {}.type
        val json = prefs.getString(key, null)
        return if (json != null) gson.fromJson(json, arrayType)
        else ArrayList()
    }

    fun setStringArray(key: String, values: MutableList<String>) {
        val gson = Gson()
        val str = gson.toJson(values)
        if (values.isNotEmpty()) {
            editor.putString(key, str)
        } else {
            editor.putString(key, null)
        }
        editor.apply()
    }

    fun getString(key: String, defValue: String?): String? {
        return prefs.getString(key, defValue)
    }

    fun setString(key: String, str: String?) {
        editor.putString(key, str).apply()
    }

    fun getInt(key: String, int: Int): Int {
        return prefs.getInt(key, int)
    }

    fun setInt(key: String, int: Int) {
        editor.putInt(key, int).commit()
    }

    fun getBoolean(key: String, boolean: Boolean): Boolean {
        return prefs.getBoolean(key, boolean)
    }

    fun setBoolean(key: String, boolean: Boolean) {
        editor.putBoolean(key, boolean).apply()
    }

    fun setObject(key: String, objectData : Any){
        val gson = Gson()
        val value = gson.toJson(objectData)
        editor.putString(key, value).apply()
    }

    fun clear() {
        editor.clear().apply()
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    companion object{
        const val PREFERNCE = "preference"
    }



    var accessToken : String get() = getString(ACCESS_TOKEN,"").toString()
    set(value) {
        setString(ACCESS_TOKEN, value)
    }
    var refreshToken : String get() = getString(REFRESH_TOKEN,"").toString()
        set(value) {
            setString(REFRESH_TOKEN, value)
        }
}