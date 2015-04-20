/**
 *
 * @param {Object} path
 * return path 大图全路径，min 小图全路径
 */
function getPhotoUrl(path) {
    if (typeof(path) != 'string') {
        return {path: "", min: ""};
    }
    if (!path || $.trim(path) == "")return {path: "", min: ""};
    path = getAssembledUrl(path);
    var index = path.lastIndexOf('.');
    var min = path.substr(0, index) + '_min.jpg';
    return {path: path, min: min};
}

function getAssembledUrl(url) {
    var staticPath = 'http://static.m.yy.com/'
    if (!url) {
        return '';
    }
    var t = url.indexOf(staticPath);
    if (t != -1) {
        return url;
    }
    return getRandomYYStaticPath() + url;
}

/**
 * 随机获取resPath
 */
function getRandomResPath() {
    var resDomain = ['http://res.1931.yystatic.com/', 'http://res0.1931.yystatic.com/', 'http://res1.1931.yystatic.com/', 'http://res2.1931.yystatic.com/'];
    var index = 0;
    // 0与4出现的概率减半,故出现0或4都当0使用
    var r = Math.round(Math.random() * 4);
    index = r % 4;
    if ((index != 0) && (index != 1) && (index != 2) && (index != 3)) {
        index = 0;
    }
    return resDomain[index];
}

/**
 * hash获取resPath
 */
function getRandomResPath(url) {
    var resDomain = ['http://res.1931.yystatic.com/', 'http://res0.1931.yystatic.com/', 'http://res1.1931.yystatic.com/', 'http://res2.1931.yystatic.com/'];
    var index = 0;
    if (url) {
        index = hashIndex(url, 4);
    }
    return resDomain[index] + url;
}

function hashIndex(str, size) {
    return Math.abs(hashCode(str)) % (size);
}

function hashCode(str) {
    str = str + "";
    var h = 0, off = 0;
    var len = str.length;

    for (var i = 0; i < len; i++) {
        h = 31 * h + str.charCodeAt(off++);
        if (h > 0x7fffffff || h < 0x80000000) {
            h = h & 0xffffffff;
        }
    }
    return h;
}

/**
 * 随机获取yystaticPath
 */
function getRandomYYStaticPath() {
    var resDomain = ['http://res.m.yystatic.com/', 'http://res0.m.yystatic.com/', 'http://res1.m.yystatic.com/', 'http://res2.m.yystatic.com/'];
    var index = 0;
    // 0与4出现的概率减半,故出现0或4都当0使用
    var r = Math.round(Math.random() * 4);
    index = r % 4;
    if ((index != 0) && (index != 1) && (index != 2) && (index != 3)) {
        index = 0;
    }
    return resDomain[index];
}

/**
 * hash获取yystaticPath
 */
function getRandomYYStaticPath(url) {
    var resDomain = ['http://res.m.yystatic.com/', 'http://res0.m.yystatic.com/', 'http://res1.m.yystatic.com/', 'http://res2.m.yystatic.com/'];
    var index = 0;
    if (url) {
        index = hashIndex(url, 4);
    }
    return resDomain[index] + url;
}
