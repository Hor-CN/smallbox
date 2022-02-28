package cn.itbk.smallbox.Manager

import android.content.Context
import android.text.TextUtils
import cn.itbk.smallbox.APP.Base.BaseConstant
import cn.itbk.smallbox.APP.SmallBoxAPP.Companion.instance
import cn.itbk.smallbox.model.User.User
import cn.itbk.smallbox.Utils.PreferenceUtil.getObject
import cn.itbk.smallbox.Utils.PreferenceUtil.saveObject

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-09
 */
class AccountManager private constructor(applicationContext: Context) {
    companion object {
        var accountInfo: User? = null
            private set
        private var sAccountManager: AccountManager? = null
        var token: String? = null
            private set

        fun init(context: Context?) {
            requireNotNull(context) { "Context should not be null!!!" }
            if (sAccountManager == null) {
                sAccountManager = AccountManager(context)
            }
        }

        val isLogin: Boolean
            get() = accountInfo != null && !TextUtils.isEmpty(token)

//        fun signInToken(token: String?) {
//            Companion.token = token
//            save(instance, BaseConstant.TAG_TOKEN, token)
//        }

        fun signIn(account: User?) {
            accountInfo = account
            saveObject(instance, BaseConstant.TAG_USER_BEAN, account)
        }

        /*更新登录信息*/
        fun updateAccountInfo(accountInfo: User?) {
            Companion.accountInfo = accountInfo
            saveObject(instance, BaseConstant.TAG_USER_BEAN, accountInfo)
        }

//        /*需要登录的判断*/
//        fun login(): Boolean {
//            return isLogin
//        }

        fun signOut() {
            //清空账户信息
            accountInfo = null
            token = ""
//            save(instance, BaseConstant.TAG_TOKEN, token)
//            save(instance, BaseConstant.TAG_TOKEN_EXPIRE_TIME, 0L)
            saveObject(instance, BaseConstant.TAG_USER_BEAN, accountInfo)
            //        PreferenceUtil.INSTANCE.saveObject(SmallBoxAPP.Companion.getInstance(), BaseConstant.TAG_USER_PROFILE, null);
        }
    }

    init {
        if (accountInfo == null) {
            accountInfo = getObject(
                applicationContext, BaseConstant.TAG_USER_BEAN, User::class.java
            )
        }
        token = "123456"
//        token = PreferenceUtil[applicationContext, BaseConstant.TAG_TOKEN]
    }
}