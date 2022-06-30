package cn.itbk.smallbox.model

data class AppUpdateModel(
    var id: Int,
    var apkname: String,
    var apkdescription: String,
    var apksize: Int,
    var apkurl: String,
    var apkversioncode: Int,
    var apkversionname: String,
    var createTime: Long,
    var forcedupgrade: Boolean,
)