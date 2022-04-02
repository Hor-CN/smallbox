package cn.itbk.smallbox.model.applet

import cn.itbk.smallbox.model.user.User
import java.io.Serializable


/**
 * @简介 小程序数据模型
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
data class AppletModel(
    var id: Int,
    var appId: String, // 小程序ID
    var name: String, // 小霸王游戏机
    var appIcon: String, // 应用图标
    var author: User, // 作者
    var description: String, // 小霸王其乐无穷！
    var downloadUrl: String,// 下载地址
    var downloadNum: Int,// 下载次数
    var commentNum: Int,// 评论次数
    var likeNum: Int,// 点赞次数
    var createTime: String,// 发布时间
    var updateTime: String // 更新时间
): Serializable
