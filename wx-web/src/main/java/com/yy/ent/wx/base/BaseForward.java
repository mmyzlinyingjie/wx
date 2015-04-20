package com.yy.ent.wx.base;


import com.yy.ent.cherrice.ret.Forward;

public class BaseForward extends Forward {
    protected static String basePath = "/WEB-INF/jsp/";

    public BaseForward(String path) {
        super(basePath + path);
    }

    public static BaseForward moblie(String path) {
        return new BaseForward("mobile/" + path);
    }
}
