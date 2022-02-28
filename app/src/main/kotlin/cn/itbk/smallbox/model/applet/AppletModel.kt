package cn.itbk.smallbox.model.applet

import cn.itbk.smallbox.model.User.User
import java.io.Serializable


/**
 * @简介 小程序数据模型
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
data class AppletModel(
    var id: Int,
    var name: String, // 小霸王游戏机
    var author: User, // 作者
    var description: String, // 小霸王其乐无穷！
    var download_url: String,// 下载地址
    var download_num: Int,// 下载次数
    var comment_num: Int,// 评论次数
    var like_num: Int,// 点赞次数
    var create_time: String,// 发布时间
    var update_time: String // 更新时间
): Serializable
