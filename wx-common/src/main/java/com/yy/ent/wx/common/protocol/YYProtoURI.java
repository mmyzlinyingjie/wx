package com.yy.ent.wx.common.protocol;

/**
 * Created by xieyong on 2014/11/10.
 */
public class YYProtoURI {

    //协议常量开始
    /**
     * 查询我的订阅列表*
     */
    public static final YYProtoURI DEMO_URI = new YYProtoURI(200, 164);

    //协议常量开始

    private int requestCid; // 请求sid
    private int responseCid; // 响应sid
    private int sid;

    public YYProtoURI(int requestCid, int responseCid, int sid) {
        this.requestCid = requestCid;
        this.responseCid = responseCid;
        this.sid = sid;
    }

    public YYProtoURI(int requestCid, int sid) {
        this.requestCid = requestCid;
        this.sid = sid;
    }

    public int getRequestURI() {
        return requestCid << 8 | sid;
    }

    public int getResponseURI() {
        return responseCid << 8 | sid;
    }

}
