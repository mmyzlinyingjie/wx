package com.yy.ent.wx.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MessageMapper {
    public MessageMapper(File propertiesFile) throws FileNotFoundException, IOException {
        readFile(propertiesFile);
    }

    public MessageMapper(String propertiesPath) throws FileNotFoundException, IOException {
        File file = new File(propertiesPath);
        readFile(file);
    }

    public MessageMapper(Map<Integer, String> messages) {
        this.messages = messages;
    }

    private void readFile(File propertiesFile) throws FileNotFoundException, IOException {
        messages = new HashMap<Integer, String>();
        Properties p = new Properties();
        p.load(new FileInputStream(propertiesFile));
        Iterator<?> its = p.keySet().iterator();
        while (its.hasNext()) {
            Object key = its.next();
            messages.put(Integer.valueOf(key.toString()), p.get(key).toString());
        }
    }

    private Map<Integer, String> messages;

    public String getMessge(int errorCode) {
        return (messages.containsKey(errorCode) ? messages.get(errorCode) : "未知错误码") + "(" + errorCode + ")";
    }
}
