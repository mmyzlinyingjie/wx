(function ($) {
    $.fn.scrollLoading = function (options) {
        var defaults = {
            attr: "data-url",
            container: $(window),
            callback: $.noop
        };
        var params = $.extend({}, defaults, options || {});
        params.cache = [];
        $(this).each(function () {
            var node = this.nodeName.toLowerCase(), url = $(this).attr(params["attr"]);
            if (url) {
                //重组
                var data = {
                    obj: $(this),
                    tag: node,
                    url: url
                };
                params.cache.push(data);
            }

        });

        var callback = function (call) {
            if ($.isFunction(params.callback)) {
                params.callback.call(call.get(0));
            }
        };
        //动态显示数据
        var loading = function () {
            if (params.cache.length == 0) {
                return;
            }
            var contHeight = params.container.height();
            if (params.container[0].constructor && params.container[0].constructor.toString() == "[object Window]") {
                contop = params.container.scrollTop();
            } else {
                try {
                    contop = params.container.offset().top;
                } catch (e) {
                    contop = params.container.scrollTop();
                }
            }
            var isLoadSource = false;
            $.each(params.cache, function (i, data) {
                if (!data) {
                    return true;
                }
                var o = data.obj, tag = data.tag, url = data.url, post, posb;

                if (url) {
                    post = o.offset().top - contop, post + o.height();
                    if ((post >= 0 && post < contHeight) || (posb > 0 && posb <= contHeight)) {
                        var cb = null;
                        if (url) {
                            //在浏览器窗口内
                            if (tag === "img") {
                                //图片，改变src
                                cb = function () {
                                    callback(o.attr("src", url));
                                    o.removeAttr(params["attr"]);
                                };
                            } else {
                                cb = function () {
                                    o.load(url, {}, function () {
                                        o.data("loadFinish", true);
                                        callback(o);
                                    });
                                };
                            }
                        } else {
                            // 无地址，直接触发回调
                            cb = function () {
                                callback(o);
                            };
                        }
                        setTimeout(cb, 1);
                        if (!isLoadSource)
                            isLoadSource = true;
                        data.url = null;
                    }
                }
            });
            if (isLoadSource) {
                var rmCb = function () {
                    $.each(params.cache, function (i, data) {
                        if (!data) {
                            return true;
                        }
                        var o = data.obj;
                        if (o && (!o.attr(params["attr"]) || o.data("loadFinish"))) {
                            data.obj = null;
                            params.cache.splice(i, 1);
                        }
                    });
                };
                setTimeout(rmCb, 1);
            }
        };

        //事件触发
        //加载完毕即执行
        loading();
        //滚动执行
        params.container.bind("scroll", loading);
        params.container.bind("resize", loading);
    };
})(jQuery);