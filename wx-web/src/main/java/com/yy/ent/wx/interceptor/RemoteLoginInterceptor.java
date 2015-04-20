package com.yy.ent.wx.interceptor;

import com.yy.ent.cherrice.Return;
import com.yy.ent.cherrice.interceptor.Interceptor;
import com.yy.ent.cherrice.invocation.ActionInvocation;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.commons.base.valid.BlankUtil;
import com.yy.ent.external.udb.CookieProcesse;
import com.yy.ent.wx.common.util.PrintLogger;
import com.yy.ent.wx.common.util.UdbEnv;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明：远程去udb进行验证;
 *
 * @create:创建时间：2013-6-26 下午11:12:59
 * @author:<a href="mailto:chenxu@yy.com">陈顼</a>
 * @version:v1.00
 */
public class RemoteLoginInterceptor implements Interceptor {

    protected final Logger m_logger = Logger.getLogger(this.getClass());

    @Inject(instance = UdbEnv.class)
    private UdbEnv udbEnv;

    @Override
    public Return intercept(ActionInvocation invocation) throws Exception {

        HttpServletRequest req = invocation.getRequest();
        HttpServletResponse rep = invocation.getResponse();
        String className = invocation.getAction().getClass().getSimpleName();
        String loginUid = udbEnv.getLoginUid(req, rep);
        String referer = invocation.getRequest().getHeader("Referer");
        String uri = req.getRequestURI();
        StringBuilder data = new StringBuilder();
        data.append("LoginInterceptor intercept className:" + className + ",uri:" + uri + ",referer:" + referer + ",loginUid:" + loginUid);
        if (BlankUtil.isBlank(loginUid)) {
            PrintLogger.printParamsLog(data, req.getParameterMap());
            return null;
        }

        JSONObject json = udbEnv.validAccessToken(req, rep);
        boolean flag = false;
        if (json != null) {
            flag = json.getBoolean("result");
        }
        if (!flag) {
            data.append(",flag:" + flag);
            PrintLogger.printParamsLog(data, req.getParameterMap());
            CookieProcesse.clearCookie(req, rep);
            return null;
        }
        return invocation.invoke();
    }

    public void destroy() {
    }

    public void init() {

    }

}
