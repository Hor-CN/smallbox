package cn.itbk.smallbox.model.Srore;

import com.chad.library.adapter.base.entity.JSectionEntity;

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-07
 */

public class ListModel extends JSectionEntity {
    private boolean isHeader;
    private Object  object;

    public ListModel(boolean isHeader, Object object) {
        this.isHeader = isHeader;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

}