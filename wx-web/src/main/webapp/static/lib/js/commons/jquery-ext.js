/**
 * JQuery扩展，主要是处理ajax异常和错误
 *
 * @author V1.0 (chengaochang@ceopen.cn)
 * @author V1.1 (wangjun1@yy.com)
 */
;
(function ($) {
    $.ajaxSetup({cache: false, traditional: true, contentType: "application/x-www-form-urlencoded; charset=UTF-8"});
    var ajax = $.ajax;
    var sendStateObj = {};
    var errorHandler = function (s, xhr) {
        var contentType = xhr.getResponseHeader("Content-Type");
        var data = xhr.responseText;

        try {
            data = eval("(" + data + ")");
        }
        catch (error) {
            data = {result: "400", msg: error};
        }

        var isHandler = false;
        //开发人员自定义异常处理
        if (s.exception) {
            s.exception(data);
            isHandler = true;
        }
        if (s.errorPage) {
            window.location.href = s.errorPage;
            isHandler = true;
            return isHandler;
        }
        if (s.autoAlert == true) {
            s.alert(data);
            isHandler = true;
        }
        return isHandler;
    };

    var hasJsonException = function (xhr) {
        return xhr.getResponseHeader("Json-Exception") == "jsExc";
    };

    $.ajax = function (s) {
        //是否等待此次请求完成再执行下次请求，防止重复提交
        if ($.trim(s.type).toLowerCase() == "post") {
            var sendState = sendStateObj[s.url];
            if (!sendState) {
                sendState = {};
                sendStateObj[s.url] = sendState;
            }
            //console.log("currentState.isSendbefore="+sendState.isSending);
            if (sendState.isSending) {
                return;
            }
            sendState.isSending = true;
            //console.log("currentState.send="+sendState.isSending);
            s.sendState = sendState;
        }
        ;

        //扩展ajax提交之前的操作
        var oldBeforeSend = s.beforeSend;
        s.beforeSend = function (xhr, s) {
            //设置是否显示遮罩效果
            if (s.showMask && top.commonShowMask) {
                top.commonShowMask();
            }
            var isSend = true;
            try {
                xhr.setRequestHeader("Request-Type", "ajax");
            } catch (e) {
            }
            if (oldBeforeSend) {
                isSend = oldBeforeSend(xhr, s);
            }
            return isSend;
        };

        //扩展成功提示
        var oldSuccess = s.success;
        s.success = function (data, status, xhr) {
            if (hasJsonException(xhr)) {
                errorHandler(s, xhr);
            } else {
                if (s.autoAlert == true && s.dataType == "json" && data && data.result != 0 && data.msg) {
                    alert(data.msg);
                    return;
                }
                //开发人员自定义成功处理
                if (oldSuccess) {
                    oldSuccess(data, status, xhr);
                }
            }
        };

        var oldComplete = s.complete;
        s.complete = function (xhr, textStatus) {
            if (s.sendState) {
                s.sendState.isSending = false;
            }

            if (oldComplete) {
                oldComplete(xhr, textStatus);
            }
        };
        var oldError = s.error;
        s.error = function (xhr, textStatus, errorThrown) {
            if (xhr.status >= 500) {
                if (s.errorPage) {
                    window.location.href = s.errorPage;
                    return;
                }

                if (hasJsonException(xhr) && errorHandler(s, xhr)) {
                    return;
                }
            }
            if (oldError) {
                oldError(xhr, textStatus, errorThrown);
            }
        };
        s.alert = function (data) {
            // 统一处理异常
            if (data.type == "ARGUMENT") {
                if ($.messagebox) {
                    $.messagebox({type: "error", msg: "参数错误:" + data.msg});
                } else {
                    alert("参数错误:" + data.msg);
                }
            } else if (data.type == "NETWORK") {
                if ($.messagebox) {
                    $.messagebox({type: "error", msg: "网络错误:" + data.msg});
                } else {
                    alert("网络错误:" + data.msg);
                }
            } else if (data.type == "BUSINESS") {
                if ($.messagebox) {
                    $.messagebox({type: "error", msg: "" + data.msg});
                } else {
                    alert("" + data.msg);
                }
            } else {
                if ($.messagebox) {
                    $.messagebox({type: "error", msg: "未知类型异常:" + data.msg});
                } else {
                    alert("未知类型异常:" + data.msg);
                }
            }
        };
        ajax(s);
    };
})(jQuery);