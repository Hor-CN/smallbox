package cn.itbk.smallbox.model.User

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-15
 */
data class User(
    var id: Long, // 用户ID
    var username: String, // 用户名
    var imgPath: String, // 图像
    var nickname: String, // 昵称
    var introduce: String, // 个性介绍
    var email: String, // 电子邮箱
    var sex: Int, // 性别
    var state: Int, // 账号状态
    var createTime: String, // 创建时间
    var updateTime: String, // 更新时间
    var tokenInfo: TokenInfo // 信息
)

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