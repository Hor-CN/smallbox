package cn.itbk.smallbox.manager

import android.content.Context
import android.text.TextUtils
import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.app.SmallBoxAPP.Companion.instance
import cn.itbk.smallbox.model.user.User
import cn.itbk.smallbox.utils.PreferenceUtil
import cn.itbk.smallbox.utils.PreferenceUtil.getObject
import cn.itbk.smallbox.utils.PreferenceUtil.save
import cn.itbk.smallbox.utils.PreferenceUtil.saveObject

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
        val isLogin: Boolean
            get() = accountInfo != null && !TextUtils.isEmpty(token)

        fun init(context: Context?) {
            requireNotNull(context) { "Context should not be null!!!" }
            if (sAccountManager == null) {
                sAccountManager = AccountManager(context)
            }
        }

        // 更新Token
        fun signInToken(token: String?) {
            Companion.token = token
            save(instance, BaseConstant.TAG_TOKEN, token)
        }

        fun signIn(account: User?) {
            accountInfo = account
            if (account != null) {
                save(instance, BaseConstant.TAG_TOKEN, account.tokenInfo.tokenValue)
            }
            saveObject(instance, BaseConstant.TAG_USER_BEAN, account)
            save(instance, BaseConstant.TAG_TOKEN, account?.tokenInfo?.tokenValue)
        }

        /*更新登录信息*/
//        fun updateAccountInfo(accountInfo: User?) {
//            Companion.accountInfo = accountInfo
//            saveObject(instance, BaseConstant.TAG_USER_BEAN, accountInfo)
//        }

        /*需要登录的判断*/
        fun login(): Boolean {
            return isLogin
        }

        fun signOut() {
            //清空账户信息
            accountInfo = null
            token = ""
            save(instance, BaseConstant.TAG_TOKEN, token)
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
        token = PreferenceUtil[applicationContext, BaseConstant.TAG_TOKEN]
    }
}