package com.yy.ent.wx.common.constants;

/**
 * 定义异常编码
 * Created by xieyong on 2014/11/6.
 */
public class ErrorCode {

    // 参数错误
    public static final int PARAM_ERROR = 1001;

    // 参数为空
    public static final int PARAM_BLANK = 1002;

    // 客户端请求与语法错误
    public static final int REQUEST_ERROR = 400;

    // 服务器收到请求，但是拒绝提供服务
    public static final int REQUEST_FORBIDDEN = 403;

    // 未找到资源
    public static final int NOT_FOUND = 404;

    // 服务器发生了不可预期的错误
    public static final int SERVER_ERROR = 500;

    // 服务器当前不能处理客户端的请求
    public static final int SERVER_UNAVAILABLE = 503;
}
