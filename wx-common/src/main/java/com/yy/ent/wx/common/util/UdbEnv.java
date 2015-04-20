package com.yy.ent.wx.common.util;

import com.duowan.udb.auth.UserinfoForOauth;
import com.yy.ent.external.udb.UdbValidate;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by xieyong on 2014/11/10.
 */
public class UdbEnv {

    private static final String CALLBACK_URL_KEY = "CALLBACK_URL";
    private static final String DENY_CALLBACK_URL_KEY = "DENY_CALLBACK_URL";
    private static final String APP_ID_KEY = "APP_ID";
    private static final String APP_KEY_KEY = "APP_KEY";


    private String udbCallBack;
    private String udbDenyCallBack;
    private String appId;
    private String appKey;


    public String getUdbCallBack() {
        return udbCallBack;
    }

    public void setUdbCallBack(String udbCallBack) {
        this.udbCallBack = udbCallBack;
    }

    public String getUdbDenyCallBack() {
        return udbDenyCallBack;
    }

    public void setUdbDenyCallBack(String udbDenyCallBack) {
        this.udbDenyCallBack = udbDenyCallBack;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    public void init(String configPath) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File(configPath)));

        udbCallBack = prop.getProperty(CALLBACK_URL_KEY);
        udbDenyCallBack = prop.getProperty(DENY_CALLBACK_URL_KEY);
        appId = prop.getProperty(APP_ID_KEY);
        appKey = prop.getProperty(APP_KEY_KEY);

    }


    public String getLoginUid(HttpServletRequest req, HttpServletResponse rep) throws Exception {
        return UdbValidate.getLoginUid(req, rep, appId, appKey);
    }

    public UserinfoForOauth userinfoForOauth(HttpServletRequest req, HttpServletResponse rep) throws Exception {
        return UdbValidate.UserinfoForOauth(req, rep, appId, appKey);
    }

    public JSONObject validAccessToken(HttpServletRequest req, HttpServletResponse rep) throws Exception {
        return UdbValidate.validAccessToken(req, rep, appId, appKey);
    }
}
