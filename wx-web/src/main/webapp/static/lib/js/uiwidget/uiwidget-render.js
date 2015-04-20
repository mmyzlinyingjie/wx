/**
 * Cherry, I come to cleanse this land
 * @author kuanglingxuan@yy.com
 */
;
(function ($) {
    $.uiwidget = $.uiwidget || {};
    $.uiwidget.Cherry = function (target, cfg) {
        $.extend(this, cfg);//本对象加载cfg属性
        this.target = target;//绑定的dom el
        this.init();//初始化
    };
    $.uiwidget.Cherry.prototype = {
        bean: null,
        list: [],
        init: function (cfg) {
            var t = this;
            if (!t.url) {
                t.render(t.json);
            } else {
                $.ajax({
                    url: t.url,
                    type: t.type,
                    dataType: 'json',
                    data: t.params,
                    success: function (json) {
                        t.render(json);
                    }
                });
            }
        },
        render: function (json) {
            var t = this;
            t.json = json;
            var target = $(t.target);
            if (t.type == "bean") {
                t._trans(t.getBean(), t.json);
            } else if (t.type == "list") {
                if (!t.sourceTemplet) {
                    t.dataTypes = target.find("[datatype='item']");
                    if (t.dataTypes.length > 0) {
                        t.sourceTemplet = $(t.dataTypes[0]).clone();
                    }
                    // console.log(t.sourceTemplet.attr('class'))
                }

                var parent = t.dataTypes.parent();
                parent.empty();
                t.dataTypes.length = 0;
                $(t.json).each(function (i, n) {
                    var clone = t.sourceTemplet.clone();

                    parent.append(clone);
                    t.dataTypes.push(clone);
                    t._trans(t.getList(clone, i)[i], t.json[i], i);
                    if (t.each) {
                        t.each(clone, i);
                    }
                });
//				if(t.dataTypes.length < t.json.length){
//				}
//				t.dataTypes = target.find("[datatype='item']");
//				t.dataTypes.each(function(i, n){
//					t._trans(t.getList(n, i)[i], t.json[i], i);
//					if(t.each){
//						t.each($(n), i);
//					}
//				});
            }
            return t;
        },
        _trans: function (dom, json, itemIndex) {
            var t = this;
            if (!json) {
                return;
            }
            dom.each(function (i, n) {
                var field = $(n).attr("datafield");
                var value = null;
                var fun = $(t.templet).attr(field);
                if ($.isFunction(fun)) {
                    value = fun($(n), json, itemIndex);
                } else {
                    value = $(json).attr(field);

                }
                // console.log(value);
                if ($.isNumeric(value)) {
                    value += "";
                }
                if ($.type(value) === "string") {
                    $(n).html(t.htmlFilter(value));
                }
            });
        },
        getBean: function () {
            var t = this;
            if (!t.bean) {
                t.bean = t.target.find("[datafield]");
            }
            return t.bean;
        },
        getList: function (n, i) {
            var t = this;
            //if(!t.list[i]){
            t.list[i] = $(n).find("[datafield]");
            //}
            return t.list;
        },
        htmlFilter: function (s) {
            if (!s)return "";
            var html = "";
            var buffer = "";
            for (var i = 0; i < s.length; i++) {
                var c = s.charAt(i);
                switch (c) {
                    case '<':
                        buffer += "&lt;";
                        break;
                    case '>':
                        buffer += "&gt;";
                        break;
                    case '&':
                        buffer += "&amp;";
                        break;
                    case '"':
                        buffer += "&quot;";
                        break;
                    case "'":
                        buffer += "&#39;";
                        break;
                    default:
                        buffer += c;
                }
            }
            html = buffer.toString();
            return html;
        }
    };

    $.fn.cherry = function (cfg) {
        return new $.uiwidget.Cherry(this, cfg);
    };
})(jQuery);