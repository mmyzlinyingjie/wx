package com.yy.ent.wx.base;

import com.alibaba.fastjson.JSON;
import com.yy.ent.cherrice.ret.Forward;
import com.yy.ent.cherrice.ret.Redirect;
import com.yy.ent.cherrice.ret.Render;
import com.yy.ent.cherrice.ret.RenderType;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.wx.common.util.UdbEnv;

import net.sf.json.JSONObject;

public class BaseAction extends com.yy.ent.cherrice.BaseAction {

    private static final String KEY_RESULT = "result";    //JSON 是否成功标识KEY  0: 失败  1：成功
    private static final String KEY_DATA = "data";        //JSON 数据KEY
    private static final String RESULT_SUCCESS = "0";      //成功
    private static final String RESULT_FAIL = "1";      //失败


    @Inject(instance = UdbEnv.class)
    private UdbEnv udbEnv;

    /**
     * getLoginUid：获取登录者的UID
     *
     * @return
     * @throws Exception
     * @author:<a href="mailto:chenxu@yy.com">Kyle</a>
     */
    protected String getLoginUid() throws Exception {
        return udbEnv.getLoginUid(getRequest(), getResponse());
    }


    private String formatData(Object data) {
        JSONObject json = new JSONObject();
        json.put(KEY_DATA, data);
        json.put(KEY_RESULT, RESULT_SUCCESS);
        return json.toString();
    }
    
    

    protected Render getRender(RenderType type, Object data) {
        return new Render(type, formatData(data));
    }


    protected Render getRender(Object data) {
    	getResponse().setHeader("Access-Control-Allow-Origin", "*");
        return new Render(RenderType.JSON, formatData(data));
    }

    protected Render getRenderFail(Object data) {
    	JSONObject json = new JSONObject();
        json.put(KEY_DATA, data);
        json.put(KEY_RESULT, RESULT_FAIL);
        getResponse().setHeader("Access-Control-Allow-Origin", "*");
        return new Render(RenderType.JSON, json.toString());
    }
    

    protected Forward getForward(String path) {
    	getResponse().setHeader("Access-Control-Allow-Origin", "*");
        return new BaseForward(path);
    }


    protected Redirect getRedirect(String path) {
        return new BaseRedirect(path);
    }


}
