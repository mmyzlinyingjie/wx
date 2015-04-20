package com.yy.ent.wx.common.util;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * PrintLogger.java
 *
 * @author <a href="mailto:chenxu@yy.com">Kyle</a>
 * @created 2014年4月15日
 */
public class PrintLogger {

    protected final static Logger m_logger = Logger.getLogger(PrintLogger.class);

    /**
     * 打印请求参数日志
     *
     * @param map
     * @throws Exception
     */
    public static void printParamsLog(StringBuilder data, Map map) throws Exception {
        StringBuilder sb = loggerCommons(data, map, null);
        m_logger.info(sb);
    }

    /**
     * 出错的时候打印出参数和日志
     *
     * @param map
     * @param e
     * @throws Exception
     */
    public static void printParamsErrorLog(StringBuilder data, Map map, Exception e) throws Exception {
        StringBuilder sb = loggerCommons(data, map, e);
        m_logger.error(sb, e);
    }

    private static StringBuilder loggerCommons(StringBuilder sb, Map map, Exception e) {

        Set<String> keys = map.keySet();
        sb.append(" params:{");
        int i = 0;
        for (String key : keys) {
            if (i != 0) {
                sb.append(",");
            }
            if (map.get(key).getClass().isArray()) {
                String[] str = (String[]) map.get(key);
                sb.append(key + ":" + Arrays.toString(str));
            }
            i++;
        }
        sb.append("}");
        return sb;
    }
}
