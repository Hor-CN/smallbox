package cn.itbk.smallbox.model.user

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-15
 */
data class User(
    var userId: Long, // 用户ID
    var avatarImg: String, // 图像
    var nikeName: String, // 昵称
    var introduce: String, // 个性介绍
    var developer: Boolean, //是否是开发者
    var createTime: Long, // 创建时间
    var tokenInfo: TokenInfo // 信息
)
//data class User(
//    var avatarImg: String,
//    var createTime: Long,
//    var developer: Boolean,
//    var introduce: String,
//    var nikeName: String,
//    var tokenInfo: TokenInfo,
//    var userId: Int
//)

data class TokenInfo(
    var isLogin: Boolean,
    var loginDevice: String,
    var loginId: String,
    var loginType: String,
    var sessionTimeout: Int,
    var tag: Any,
    var tokenActivityTimeout: Int,
    var tokenName: String,
    var tokenSessionTimeout: Int,
    var tokenTimeout: Int,
    var tokenValue: String
)