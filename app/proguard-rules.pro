
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep public class * extends io.dcloud.common.DHInterface.IFeature
-keep public class * extends io.dcloud.common.DHInterface.IBoot
-keep public class * extends io.dcloud.common.DHInterface.IReflectAble

-keep class com.kongzue.dialogx.** { *; }
-dontwarn com.kongzue.dialogx.**
# 额外的，建议将 android.view 也列入 keep 范围：
-keep class android.view.** { *; }
# 若启用模糊效果，请增加如下配置：
-dontwarn androidx.renderscript.**
-keep public class androidx.renderscript.** { *; }

-keep class androidx.recyclerview.widget.**{*;}
-keep class androidx.viewpager2.widget.**{*;}
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**


-dontwarn
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends io.dcloud.common.DHInterface.IDPlugin
-keep public class * extends io.dcloud.common.DHInterface.IFeature
-keep public class * extends io.dcloud.common.DHInterface.IBoot
-keep public class * extends io.dcloud.common.DHInterface.IReflectAble

-keep class io.dcloud.** {*;}
-dontwarn io.dcloud.**


-keep class vi.com.gdi.** {*;}
-keep class android.support.v4.** {*;}

#-keepclasseswithmembers class io.dcloud.appstream.StreamAppManager {
#    public protected <methods>;
#}

-keep public class * extends io.dcloud.common.DHInterface.IReflectAble{
  public protected <methods>;
  public protected *;
}
-keep class **.R
-keep class **.R$* {
    public static <fields>;
}
-keep public class * extends io.dcloud.common.DHInterface.IJsInterface{
  public protected <methods>;
  public protected *;
}

-keepclasseswithmembers class io.dcloud.EntryProxy {
    <methods>;
}

-keep class * implements android.os.IInterface {
  <methods>;
}

-keepclasseswithmembers class *{
  public static java.lang.String getJsContent();
}

-keepclasseswithmembers class *{
  public static io.dcloud.share.AbsWebviewClient getWebviewClient(io.dcloud.share.ShareAuthorizeView);
}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated, SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class * extends android.app.Application{
  public static <methods>;
  public *;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
   public static <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class dc.** {*;}
-keep class okio.**{*;}
-keep class org.apache.** {*;}
-keep class org.json.** {*;}
-keep class net.ossrs.** {*;}
-keep class android.** {*;}
-keep class com.facebook.**{*;}
-keep class com.bumptech.glide.**{*;}
-keep class com.alibaba.fastjson.**{*;}
-keep class com.sina.**{*;}
-keep class com.weibo.ssosdk.**{*;}
-keep class com.asus.**{*;}
-keep class com.bun.**{*;}
-keep class com.heytap.**{*;}
-keep class com.huawei.**{*;}
-keep class com.netease.**{*;}
-keep class com.meizu.**{*;}
-keep class com.samsung.**{*;}
-keep class com.zui.**{*;}
-keep class com.amap.**{*;}
-keep class com.autonavi.**{*;}
-keep class pl.droidsonroids.gif.**{*;}
-keep class com.tencent.**{*;}
-keep class com.baidu.**{*;}
-keep class com.iflytek.**{*;}
-keep class com.umeng.**{*;}
-keep class tv.**{*;}
-keep class master.**{*;}
-keep class uk.co.**{*;}
-keep class com.dmcbig.**{*;}
-keep class org.mozilla.**{*;}
-keep class androidtranscoder.**{*;}
-keep class XI.**{*;}


-dontwarn android.**
-dontwarn com.tencent.**

#-keep class * implements com.taobao.weex.IWXObject{*;}
-keep public class * extends com.taobao.weex.common.WXModule{*;}


-keepattributes Signature

-dontwarn org.codehaus.mojo.**
-dontwarn org.apache.commons.**
-dontwarn com.amap.**
-dontwarn com.sina.weibo.sdk.**
-dontwarn com.alipay.**
-dontwarn com.lucan.ajtools.**
-dontwarn pl.droidsonroids.gif.**

-keep class com.taobao.weex.** { *; }
-keep class com.taobao.gcanvas.**{*;}
-dontwarn com.taobao.weex.**
-dontwarn com.taobao.gcanvas.**

