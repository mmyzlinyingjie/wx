package com.yy.ent.wx.interceptor;


import com.yy.ent.cherrice.Return;
import com.yy.ent.cherrice.interceptor.Interceptor;
import com.yy.ent.cherrice.invocation.ActionInvocation;
import com.yy.ent.commons.base.http.HttpUtil;
import com.yy.ent.wx.common.constants.Constants;
import org.apache.log4j.Logger;
import com.yy.ent.wx.service.AuthorizationService;

import javax.servlet.http.HttpServletRequest;

public class AuthInterceptor implements Interceptor {
    private static final Logger m_logger = Logger.getLogger(AuthInterceptor.class);

    @Override
    public Return intercept(ActionInvocation invocation)
            throws Exception {
        HttpServletRequest request = invocation.getRequest();
        String ip = HttpUtil.getIpAddr(request);
        if (AuthorizationService.isAccess(ip, Constants.PC_KEY) || AuthorizationService.isAccess(ip, Constants.MOBILE_KEY)) {
            return invocation.invoke();
        } else {
            m_logger.warn("AuthInterceptor intercept ip:" + ip + " not in white list");
            throw new Exception("ip:" + ip + " not in white list");
        }

    }

    public void destroy() {
    }

    public void init() {

    }
}
