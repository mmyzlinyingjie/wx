var loginCfg = {
    loginCallBack: null,
    logoutCallBack: null,
    closeLoginCallBack: null
};
/**
 * 弹出登录窗口
 */
function showLoginBox() {
//	UDB.sdk.QLogin.ajaxOpenWithSaveToken('/dream/getSdkAuth.action');
    UDB.sdk.PCWeb.popupOpenLgn('/dream/getSdkAuth.action');
}

/**
 * 登录后刷新的相关操作
 * @return
 */
function refreshOperate(cancel, writeCookieURL) {
    if (cancel == "1") {
        if (loginCfg.closeLoginCallBack) {
            loginCfg.closeLoginCallBack();
            return false;
        }
    } else {
//		UDB.sdk.QLogin.writeCrossmainCookieWithCallBack(writeCookieURL,function(){
        UDB.sdk.PCWeb.writeCrossmainCookieWithCallBack(writeCookieURL, function () {
            $(this).unbind("load"); //解决udb加载事件重复绑定的bug
            showLoginInfo();
            if (loginCfg.loginCallBack) {
                loginCfg.loginCallBack();
            }
        });
    }
}

/**
 * 1931登录成功后，公共回调方法处理
 */
function showLoginInfo() {
    initLoginStatus();
}

// 初始化用户昵称
function initUserNick() {
    var nick = $.trim($('#loginNick').text());
    if (nick != null && nick != '') {
        return;
    }
    var t = new Date();
    var params = {'t': t};
    var url = '/dream/web/getUserNick.action';
    $.post(url, params, function (data) {
        var json = data;//JSON.parse(data);
        if (json.code == '1') {
            $('#loginNick').text(json.data);
        }
    });
}

// 初始化用户登录态
function initLoginStatus() {
    var error = false;
    try {
        var logined = isLogin();
        if (logined) {
            initUserNick();
            $('#no-login').hide();
            $('#login-ok').show();
        } else {
            $('#loginNick').text('');
            $('#login-ok').hide();
            $('#no-login').show();
        }
    } catch (e) {
        error = true;
    }
}


/**
 * 退出登录
 */
function logout() {
    $.ajax({
        url: "/dream/logout.action",
        type: "get",
        success: function (json) {
            initLoginStatus();
//			UDB.sdk.QLogin.deleteCrossmainCookie(json.deleteCookieURL);
            UDB.sdk.PCWeb.deleteCrossmainCookieWithCallBack(json.deleteCookieURL, function () {
                if (loginCfg.logoutCallBack) {
                    loginCfg.logoutCallBack();
                }
            });
        }
    });
}

function isLogin() {
    var flag = false;
//	var loginUserName = $.cookie('username');
    var yyuid = $.cookie('yyuid');
    if (yyuid && yyuid != '') {
        flag = true;
    }
    return flag;
}

function getLoginUserName() {
    return $.cookie('username');
}

function getLoginUid() {
    return $.cookie('yyuid');
}
