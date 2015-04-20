package com.yy.ent.wx.interceptor;

import com.yy.ent.cherrice.interceptor.AbstractFileUploadInterceptor;


public class UploadInterceptor extends AbstractFileUploadInterceptor {

    @Override
    protected String getSaveDir() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected long getMaximumSize() {
        // TODO Auto-generated method stub
        return 1024 * 1024 * 5;
    }


}
