package cn.itbk.smallbox.module.main.home;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-20
 */
class ResultContract extends ActivityResultContract<Boolean, Intent> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Boolean input) {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }
    @Override
    public Intent parseResult(int resultCode, @Nullable Intent intent) {
        return intent;
    }
}