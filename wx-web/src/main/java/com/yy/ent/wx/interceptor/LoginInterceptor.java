package com.yy.ent.wx.interceptor;

import com.yy.ent.cherrice.Return;
import com.yy.ent.cherrice.interceptor.Interceptor;
import com.yy.ent.cherrice.invocation.ActionInvocation;
import com.yy.ent.cherrice.ret.Render;
import com.yy.ent.cherrice.ret.RenderType;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.commons.base.valid.BlankUtil;
import com.yy.ent.wx.common.util.UdbEnv;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明：通过udb提供的jar服务器本地验证;
 *
 * @create:创建时间：2013-6-26 下午11:13:21
 * @author:<a href="mailto:chenxu@yy.com">陈顼</a>
 * @version:v1.00
 */
public class LoginInterceptor implements Interceptor {

    protected final Logger m_logger = Logger.getLogger(this.getClass());

    @Inject(instance = UdbEnv.class)
    private UdbEnv udbEnv;

    @Override
    public Return intercept(ActionInvocation invocation) throws Exception {

        HttpServletRequest req = invocation.getRequest();
        HttpServletResponse rep = invocation.getResponse();
        String referer = req.getHeader("Referer");
        String method = invocation.getActionMethod().getName();
        String loginUid = udbEnv.getLoginUid(req, rep);
        if (BlankUtil.isBlank(loginUid)) {
            m_logger.info("LoginInterceptor validAccessToken is fail! udb yyuid:" + loginUid + " method:" + method + ",referer:" + referer);
            return new Render(RenderType.JSON, "{result : 400, msg : 'login fail'}");
        }
        return invocation.invoke();
    }

    public void destroy() {
    }

    public void init() {

    }

}
