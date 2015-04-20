package com.yy.ent.wx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yy.ent.cherrice.util.BlankUtil;
import com.yy.ent.commons.base.JsonUtil;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.commons.protopack.util.Uint;
import com.yy.ent.commons.yypclient.adapter.ClientRequest;
import com.yy.ent.commons.yypclient.adapter.ClientResponse;
import com.yy.ent.wx.common.model.Demo;
import com.yy.ent.wx.common.model.Image;
import com.yy.ent.wx.common.protocol.CacheKey;
import com.yy.ent.wx.common.protocol.YYProtoURI;
import com.yy.ent.wx.dao.ImageDao;
import com.yy.ent.wx.dao.constants.DBKey;
import com.yy.ent.wx.service.base.BaseService;

/**
 * 样例服务
 *
 * @author xieyong
 */
public class ImageService extends BaseService {

    private static final String STATE_SUCCESS = "4";

    @Inject(instance = ImageDao.class)
    private ImageDao dao;


    /**
     * 保存
     *
     * @param demo
     * @return
     * @throws Exception
     */
    public int save(Image demo) throws Exception {
        int num = insert(demo);
        setCache(demo);
        return num;
    }

    /**
     * 存缓存
     *
     * @param demo
     * @throws Exception
     */
    private void setCache(Image demo) throws Exception {
        String jsonString = JsonUtil.toString(demo);
        redisEnv.hset(CacheKey.SIGN_UP.getKey(), String.valueOf(demo.getId()), jsonString);
    }

    /**
     * 新增入库
     *
     * @param demo
     * @return
     * @throws Exception
     */
    public int insert(Image demo) throws Exception {
        int ret = dao.insert(DBKey.DEMO_DB_KEY, demo);
        return ret != 0 ? 0 : 1;
    }

    /**
     * 查询
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public Image query(String uid) throws Exception {
    	Image demoCache = getCache(uid);
        if (demoCache != null) {
            return demoCache;
        }

        Image demo = dao.query(uid);
        if(!BlankUtil.isBlank(demo)){
            setCache(demo);
        }
        return demo;
    }

    /**
     * 更新
     * @param demo
     * @return
     * @throws Exception
     */
    public int update(Image demo) throws  Exception{
        int ret = 0;
        if(!BlankUtil.isBlank(demo)){
            ret = dao.update(demo);
            setCache(demo);
        }
        return ret;
    }

    /**
     * 删除
     * @param demo
     * @return
     * @throws Exception
     */
    public int delete(Image demo) throws Exception{
        if(!BlankUtil.isBlank(demo)){
            delCache("" + demo.getId());
        }
        return dao.delete(demo);
    }

    /**
     * 从缓存获取数据
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public Image getCache(String uid) throws Exception {
        String ret = redisEnv.hget(CacheKey.SIGN_UP.getKey(), uid);
        if (ret != null) {
            return JsonUtil.toObject(ret, Image.class);
        } else {
            return null;
        }
    }

    public void delCache(String uid)  throws Exception {
        redisEnv.hdel(CacheKey.SIGN_UP.getKey(), uid);
    }

    /**
     * entProxy 使用范例
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public List<Map<Uint, String>> entProxyDemo(String uid, int offset, int pageSize) throws Exception {

        ClientRequest req = new ClientRequest(YYProtoURI.DEMO_URI.getRequestURI());
        req.putUint(new Uint(uid));
        req.putUint(new Uint(offset));
        req.putUint(new Uint(pageSize));
        req.putMap(new HashMap<String, Uint>(), String.class, Uint.class);

        ClientResponse resp = entProxy.sendAndWait(req);
        assertResult(resp);

        List<Map<Uint, String>> list = resp.popListMap(Uint.class,
                String.class);
        resp.popMap(Uint.class, String.class);
        return list;
    }

    /**
     * webdb 使用范例
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public String webdbDemo(String uid) throws Exception {
        return webdb.getUserNickByUid(uid);
    }

    /**
     * cacheProxy 使用范例
     *
     * @param key
     * @return
     * @throws Exception
     */
    public long cpDemo(String key) throws Exception {
        return cacheProxy.zcountAll(key);
    }

    /**
     * stateProxy 使用范例
     *
     * @param key
     * @return
     * @throws Exception
     */
    public long spDemo(String key) throws Exception {
        return stateProxy.zcountAll(key);
    }

}