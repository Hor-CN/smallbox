package cn.itbk.smallbox.utils

import android.content.Context
import android.text.TextUtils
import androidx.preference.PreferenceManager
import com.alibaba.fastjson.JSON
import rxhttp.wrapper.utils.GsonUtil
import java.util.*

/**
 * @简介 PreferenceUtil
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-09
 */
object PreferenceUtil {
    fun save(context: Context?, tag: String?, text: String?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.putString(tag, text)
        editor.apply()
    }

    fun save(context: Context?, tag: String?, value: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.putInt(tag, value)
        editor.apply()
    }

    fun save(context: Context?, tag: String?, value: Float) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.putFloat(tag, value)
        editor.apply()
    }

    fun save(context: Context?, tag: String?, value: Long) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.putLong(tag, value)
        editor.apply()
    }

    @JvmOverloads
    operator fun get(context: Context?, tag: String?, defString: String? = ""): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        return sp.getString(tag, defString)
    }

    operator fun get(context: Context?, tag: String?, defaultValue: Float): Float {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        return sp.getFloat(tag, defaultValue)
    }

    operator fun get(context: Context?, tag: String?, defaultValue: Int): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        return sp.getInt(tag, defaultValue)
    }

    operator fun get(context: Context?, tag: String?, defaultValue: Long): Long {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        return try {
            sp.getLong(tag, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun save(context: Context?, tag: String?, value: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.putBoolean(tag, value)
        editor.apply()
    }

    operator fun get(context: Context?, tag: String?, defValue: Boolean): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        return sp.getBoolean(tag, defValue)
    }

    fun <T> saveListObject(context: Context?, key: String?, list: List<T>?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val edit = sp.edit()
        if (list != null && list.isNotEmpty()) {
            val str = JSON.toJSONString(list)
            edit.putString(key, str)
        } else {
            edit.putString(key, "")
        }
        edit.apply()
    }

    fun saveStringSet(context: Context?, key: String?, set: Set<String?>?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val edit = sp.edit()
        edit.putStringSet(key, set)
        edit.apply()
    }

    fun getStringSet(
        context: Context?,
        key: String?
    ): Set<String>? {
        val sp =
            PreferenceManager.getDefaultSharedPreferences(
                context!!
            )
        sp.edit().apply()
        return sp.getStringSet(key, null)
    }

    fun <T> getListObject(context: Context?, key: String?, clazz: Class<T>?): List<T> {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        try {
            val json = sp.getString(key, null)
            if (!TextUtils.isEmpty(json)) {
                return JSON.parseArray(json, clazz)
            }
        } catch (e: Exception) {
        }
        return ArrayList()
    }

    fun <T> saveObject(context: Context?, key: String?, obj: T?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val edit = sp.edit()
        if (obj == null) {
            edit.putString(key, "")
        } else {
            val str = JSON.toJSONString(obj)
            edit.putString(key, str)
        }
        edit.apply()
    }

    fun <T> getObject(context: Context?, key: String?, clazz: Class<T>?): T {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val json = sp.getString(key, "")

        return GsonUtil.fromJson(json, clazz)
    }

    fun clearUser(context: Context?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(
            context!!
        )
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}