package com.yy.ent.wx.base;


import com.yy.ent.cherrice.ret.Redirect;

public class BaseRedirect extends Redirect {
    protected static String basePath = "/WEB-INF/jsp/";
    protected static String errorBasePath = "/wx/html/";

    public BaseRedirect(String path) {
        super(errorBasePath + path);
    }
}
