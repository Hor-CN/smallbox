package cn.itbk.smallbox.model.applet

import java.io.Serializable

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
data class AppletConfigModel(
    var id : String, // 唯一标识
    var name : String, // 项目名称
    var icon : String, // 图标
    var type : List<String> = listOf("test"), // 项目类型
    var description  : String = "暂无描述", // 项目描述
//    var developer: Developer, // 作者
//    var version : Version, // 版本信息
) : Serializable
data class Developer(
    var name: String,
    var email: String,
    var url: String
) : Serializable
data class Version(
    var code: String,
    var name: String
) : Serializable
