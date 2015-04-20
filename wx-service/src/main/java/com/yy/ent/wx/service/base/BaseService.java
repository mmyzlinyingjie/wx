package com.yy.ent.wx.service.base;

import com.yy.ent.clients.cache.redis.RedisCCache;
import com.yy.ent.clients.dfs.ha.FastDfsProxyHalbService;
import com.yy.ent.clients.state.redis.YYClientState;
import com.yy.ent.commons.base.cache.RedisEnv;
import com.yy.ent.commons.base.exception.BusinessException;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.commons.base.thread.ThreadTaskExecutor;
import com.yy.ent.commons.yypclient.adapter.ClientResponse;
import com.yy.ent.external.service.EntProxyHalbService;
import com.yy.ent.external.service.WebdbHalbService;
import org.apache.log4j.Logger;

public class BaseService {
    protected final Logger logger = Logger.getLogger(getClass());

    public static final int SUCCESS = 0;

    @Inject(instance = EntProxyHalbService.class)
    protected EntProxyHalbService entProxy;

    @Inject(instance = YYClientState.class)
    protected YYClientState stateProxy;

    @Inject(instance = RedisCCache.class)
    protected RedisCCache cacheProxy;

    @Inject(instance = RedisEnv.class)
    protected RedisEnv redisEnv;

    @Inject(instance = WebdbHalbService.class)
    protected WebdbHalbService webdb;
    
    @Inject(instance = FastDfsProxyHalbService.class)
    private FastDfsProxyHalbService dfsService;
    
    @Inject(instance = ThreadTaskExecutor.class)
    protected  ThreadTaskExecutor executor = new ThreadTaskExecutor();

    /**
     * 验证业务异常
     */
    public void assertResult(ClientResponse rsp) throws BusinessException {
        int result = rsp.popInteger();
        if (result != SUCCESS) {
            throw new BusinessException(rsp.getHeader().toString(), result);
        }
    }

    /**
     * 项目中确实需要单独的线程池用该方法
     * @param corePoolSize
     * @param maximumPoolSize
     * @return
     */
    public ThreadTaskExecutor getCustomExecutor(int corePoolSize, int maximumPoolSize){
        return new ThreadTaskExecutor(corePoolSize,maximumPoolSize);
    }

}
