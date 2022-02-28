package cn.itbk.smallbox.model

data class AppUpdateModel(
    var apkName: String,
    var apkUrl: String,
    var apkSize: Int,
    var apkVersionCode: Int,
    var apkDescription: String,
    var apkVersionName: String,
    var forcedUpgrade: Boolean
)