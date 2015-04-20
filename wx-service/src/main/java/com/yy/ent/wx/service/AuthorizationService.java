package com.yy.ent.wx.service;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 权限判断
 *
 * @author:<a href="mailto:laiguangquan@yy.com">Alex Lai</a>
 * @created 2013-10-25 上午10:50:29
 * @version:v1.00
 */
public class AuthorizationService {
    private static Properties properties;

    public static void init(String config) throws Exception {
        properties = new Properties();
        if (config.toLowerCase().endsWith(".xml")) {
            properties.loadFromXML(new FileInputStream(config));
        } else {
            properties.load(new FileInputStream(config));
        }
    }

    public static boolean isAccess(String accIP, String key) throws Exception {
        String[] ipValue = properties.getProperty(key).trim().split("\n");
        for (String ip : ipValue) {
            if (ip.trim().equals(accIP)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取配置文件中某个key的值
     *
     * @param key 键名
     * @return
     * @throws Exception
     */
    public static String getKeyValue(String key) throws Exception {
        if (null != properties.get(key)) {
            return String.valueOf(properties.get(key)).trim();
        }
        return null;
    }

    /**
     * 取配置文件中某个key的值
     *
     * @param key          键名
     * @param defaultValue 默认值
     * @return
     * @throws Exception
     */
    public static String getKeyValue(String key, String defaultValue) {
        try {
            if (AuthorizationService.properties.containsKey(key)) {
                return String.valueOf(properties.get(key)).trim();
            } else {
                return defaultValue;
            }
        } catch (Exception ex) {
            return defaultValue;
        }
    }


    /**
     * 取配置文件中某个key的值
     *
     * @param key 键名
     * @return
     * @throws Exception
     */
    public static int getKeyIntValue(String key) throws Exception {
        if (null != properties.get(key)) {
            return Integer.parseInt(String.valueOf(properties.get(key)).trim());
        } else {
            return 0;
        }
    }

}
