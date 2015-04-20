package com.yy.ent.wx.common.protocol;

import com.yy.ent.cherrice.util.BlankUtil;

/**
 * Created by xieyong on 2014/11/7.
 */
public enum CacheKey {

    SIGN_UP,
    SIGN_UP_STATE;

    /**
     * 例如 项目名 + "_" + 枚举名
     *
     * @return
     */
    public String getKey() {
        return ("wx_" + name()).toLowerCase();
    }

    public String getKey(String suffix) {
        if (BlankUtil.isBlank(suffix)) {
            return getKey();
        } else {
            return getKey() + "_" + suffix;
        }

    }


}
