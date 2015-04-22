package com.yy.ent.wx.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.yy.ent.cherroot.core.callback.IPreparedStatementSetter;
import com.yy.ent.cherroot.entity.callback.PreparedStatementSetterImpl;
import com.yy.ent.cherroot.entity.callback.SetterUtil;
import com.yy.ent.cherroot.ext.YYGenericDaoImpl;
import com.yy.ent.cherroot.ext.callback.PropertyRsExtractorAndMapperImpl;
import com.yy.ent.commons.base.date.DateUtil;
import com.yy.ent.commons.base.dto.Property;
import com.yy.ent.commons.base.inject.Inject;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 多表操作dao
 * 
 * @author suzhihua
 * @date 2015年3月20日 上午11:54:22
 */
public class MultiDao {
    @Inject(instance = YYGenericDaoImpl.class)
    protected YYGenericDaoImpl yyGenericDaoImpl;
    protected final Logger logger = Logger.getLogger(getClass());

    private Properties configuration;
    private Configuration ftl;
    private String conf;

    /**
     * 初始化sql配置文件
     * 
     * @param conf
     * @author suzhihua
     * @date 2015年3月20日 上午11:43:10
     */
    public MultiDao(String conf) {
        this.conf = conf;
        init();
    }

    /**
     * 初始化
     * 
     * @param conf
     * @author suzhihua
     * @date 2015年3月25日 下午9:00:08
     */
    private void init() {
        Properties all = new Properties();
        FileInputStream is = null;
        try {
        	System.out.println("=================================================");
        	System.out.println(conf);
            File file = new File(conf);
            File[] listFiles = file.getParentFile().listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".xml");
                }
            });
            for (File file2 : listFiles) {
                Properties c = new Properties();
                is = new FileInputStream(file2);
                c.loadFromXML(is);
                IOUtils.closeQuietly(is);
                Iterator<Entry<Object, Object>> iterator = c.entrySet().iterator();
                Entry<Object, Object> entry;
                while (iterator.hasNext()) {
                    entry = (Entry<Object, Object>) iterator.next();
                    if (all.get(entry.getKey()) != null) {
                        logger.warn("sql key = " + entry.getKey() + " 重复");
                    }
                    all.put(entry.getKey(), entry.getValue());
                }
            }
            this.configuration = all;
            initFtl();
        } catch (Exception e) {
            IOUtils.closeQuietly(is);
            logger.warn("加载文件出错", e);
        }
    }

    /**
     * 初始化freemarker模样引擎
     * 
     * @author suzhihua
     * @date 2015年4月9日 上午11:55:55
     */
    private void initFtl() {
        ftl = new Configuration();
        ftl.setDefaultEncoding("utf-8");
        ftl.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        ftl.setObjectWrapper(new DefaultObjectWrapper());

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        Iterator<Entry<Object, Object>> iterator = configuration.entrySet().iterator();
        Entry<Object, Object> entry;
        while (iterator.hasNext()) {
            entry = (Entry<Object, Object>) iterator.next();
            stringLoader.putTemplate((String) entry.getKey(), (String) entry.getValue());
        }
        ftl.setTemplateLoader(stringLoader);
    }

    /**
     * 重新加载
     * 
     * @author suzhihua
     * @date 2015年3月25日 下午9:00:38
     */
    public MultiDao reload() {
        init();
        return this;
    }

    /**
     * 取sql
     * 
     * @param key
     * @return
     * @author suzhihua
     * @date 2015年3月20日 上午11:43:13
     */
    public String getSql(String key) {
        String property = configuration.getProperty(key);
        System.out.println("------------进来了--------------------");
        if (property == null) {
        	System.out.println("------------空--------------------");
            throw new RuntimeException("找不到key=" + key + "的sql语句.");
        }
        return property;
    }

    /**
     * 删除
     * 
     * @param key
     * @param holder
     * @return
     * @author suzhihua
     * @date 2015年4月7日 下午4:42:47
     */
    public int delete(String key, Object... holder) {
        int delete = 0;
        try {
            delete = yyGenericDaoImpl.delete("", getSql(key), new PreparedStatementSetterImpl(holder));
        } catch (Exception e) {
            logger.warn("删除数据库出错", e);
        }
        return delete;
    }

    /**
     * 更新
     * 
     * @param key
     * @param holder
     * @return
     * @author suzhihua
     * @date 2015年4月7日 下午4:42:53
     */
    public int update(String key, Object... holder) {
        int update = 0;
        try {
            update = yyGenericDaoImpl.update("", getSql(key), new PreparedStatementSetterImpl(holder));
        } catch (Exception e) {
            logger.warn("更新数据库出错", e);
        }
        return update;
    }

    /**
     * 查询返回列表,无数据/异常时返回size=0的列表
     * 
     * @param key:配置文件sql key
     * @param holder:参数列表
     * @return
     * @author suzhihua
     * @date 2015年3月20日 上午9:48:11
     */
    public List<Property> queryCollection(String key, Object... holder) {
        List<Property> queryCollection = null;
        try {
            queryCollection = yyGenericDaoImpl.queryCollection("", getSql(key), new PreparedStatementSetterImpl(holder), new PropertyRsExtractorAndMapperImpl());
        } catch (Exception e) {
            logger.warn("查询数据库出错", e);
            queryCollection = Collections.emptyList();
        }
        return queryCollection;
    }

    /**
     * 查询返回数据行,无数据/异常时返回size=0的Property
     * 
     * @param key:配置文件sql key
     * @param holder:参数列表
     * @return
     * @author suzhihua
     * @date 2015年3月20日 上午9:48:44
     */
    public Property query(String key, Object... holder) {
        Property query = null;
        try {
            query = yyGenericDaoImpl.query("", getSql(key), new PreparedStatementSetterImpl(holder), new PropertyRsExtractorAndMapperImpl());
        } catch (Exception e) {
            logger.warn("查询数据库出错", e);
            query = new Property();
        }
        return query;
    }

    /**
     * 删除
     * 
     * @param key
     * @param holder
     * @return
     * @author suzhihua
     * @date 2015年4月7日 下午4:42:47
     */
    public int deleteFtl(String key, Map<String, Object> holder) {
        int delete = 0;
        try {
            FtlHandler ftlHandler = new FtlHandler(ftl.getTemplate(key), holder);
            delete = yyGenericDaoImpl.delete("", ftlHandler.getSql(), ftlHandler.getPreparedStatementSetter());
        } catch (Exception e) {
            logger.warn("删除数据库出错", e);
        }
        return delete;
    }

    /**
     * 更新
     * 
     * @param key
     * @param holder
     * @return
     * @author suzhihua
     * @date 2015年4月7日 下午4:42:53
     */
    public int updateFtl(String key, Map<String, Object> holder) {
        int update = 0;
        try {
            FtlHandler ftlHandler = new FtlHandler(ftl.getTemplate(key), holder);
            update = yyGenericDaoImpl.update("", ftlHandler.getSql(), ftlHandler.getPreparedStatementSetter());
        } catch (Exception e) {
            logger.warn("更新数据库出错", e);
        }
        return update;
    }

    /**
     * 查询返回列表,无数据/异常时返回size=0的列表
     * 
     * @param key:配置文件sql key
     * @param holder:参数列表
     * @return
     * @author suzhihua
     * @date 2015年3月20日 上午9:48:11
     */
    public List<Property> queryCollectionFtl(String key, Map<String, Object> holder) {
        List<Property> queryCollection = null;
        try {
            FtlHandler ftlHandler = new FtlHandler(ftl.getTemplate(key), holder);
            queryCollection = yyGenericDaoImpl.queryCollection("", ftlHandler.getSql(), ftlHandler.getPreparedStatementSetter(), new PropertyRsExtractorAndMapperImpl());
        } catch (Exception e) {
            logger.warn("查询数据库出错", e);
            queryCollection = Collections.emptyList();
        }
        return queryCollection;
    }

    /**
     * 查询返回数据行,无数据/异常时返回size=0的Property,占位符为:XX
     * 
     * @param key:配置文件sql key
     * @param holder:参数列表
     * @return
     * @author suzhihua
     * @date 2015年3月20日 上午9:48:44
     */
    public Property queryFtl(String key, Map<String, Object> holder) {
        Property query = null;
        try {
            FtlHandler ftlHandler = new FtlHandler(ftl.getTemplate(key), holder);
            query = yyGenericDaoImpl.query("", ftlHandler.getSql(), ftlHandler.getPreparedStatementSetter(), new PropertyRsExtractorAndMapperImpl());
        } catch (Exception e) {
            logger.warn("查询数据库出错", e);
            query = new Property();
        }
        return query;
    }
}


/**
 * 处理sql模板
 * 
 * @author suzhihua
 * @date 2015年4月9日 下午12:57:09
 */
class FtlHandler {
    protected static final Logger LOGGER = Logger.getLogger(FtlHandler.class);
    protected static final String HOLDER_REG = ":([^;\\s]+)";
    private String sql;
    private List<Object> holder = new ArrayList<Object>();

    public FtlHandler(Template tpl, Map<String, Object> holder) {
        Writer out = new StringWriter(512);
        StringBuffer sb = new StringBuffer();
        try {
            tpl.process(holder, out);
            sql = compressSql(out.toString());
            makeSql(holder, sb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(tpl.getName() + ": " + sql);
            LOGGER.info("params: " + holderToString(holder));
        }
        sql = sb.toString();
    }


    /**
     * 生成处理后sql及holder
     * 
     * @param holder
     * @param sb
     * @author suzhihua
     * @date 2015年4月9日 下午1:00:35
     */
    private void makeSql(Map<String, Object> holder, StringBuffer sb) {
        if (holder != null && !holder.isEmpty()) {
            Pattern p = Pattern.compile(HOLDER_REG);
            Matcher m = p.matcher(sql);
            String group1;
            Object value;
            while (m.find()) {
                group1 = m.group(1);
                value = holder.get(group1);
                if (value != null) {
                    m.appendReplacement(sb, "?");
                    this.holder.add(value);
                } else {
                    throw new RuntimeException("key=" + group1 + "为空");
                }
            }
            m.appendTail(sb);
        }
    }

    public String getSql() {
        return sql;
    }

    public IPreparedStatementSetter getPreparedStatementSetter() {
        return new ListPreparedStatement(holder);
    }


    /**
     * 参数转为字串打印
     * 
     * @param holder
     * @return
     * @author suzhihua
     * @date 2015年4月9日 下午1:15:59
     */
    private String holderToString(Map<String, Object> holder) {
        Iterator<Entry<String, Object>> iterator = holder.entrySet().iterator();
        Entry<String, Object> next;
        Object value;
        StringBuilder sb = new StringBuilder("{");
        while (true) {
            next = iterator.next();
            value = next.getValue();
            sb.append(next.getKey()).append("=(");
            sb.append(value.getClass().getSimpleName()).append(")");
            if (value.getClass().isAssignableFrom(Date.class)) {
                sb.append(DateUtil.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss"));
            } else {
                sb.append(value);
            }

            if (iterator.hasNext()) {
                sb.append(", ");
            } else {
                sb.append("}");
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 压缩精简sql
     * 
     * @param sql
     * @return
     * @author suzhihua
     * @date 2015年4月9日 下午1:14:55
     */
    private String compressSql(String sql) {
        return sql.trim().replaceAll("\\s+", " ");
    }

}


/**
 * 直接入参list占位符
 * 
 * @see PreparedStatementSetterImpl
 * @author suzhihua
 * @date 2015年4月9日 上午11:57:41
 */
class ListPreparedStatement implements IPreparedStatementSetter {
    private static Logger m_logger = Logger.getLogger(ListPreparedStatement.class);


    private List<Object> values;

    public ListPreparedStatement(List<Object> values) {
        this.values = values;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        if (values == null || values.size() == 0) {
            return;
        }

        try {
            int index = 1;
            for (Object value : values) {
                SetterUtil.invokeSet(ps, value.getClass(), value, index);
                index++;
                if (m_logger.isDebugEnabled()) {
                    m_logger.debug("value is: " + value.toString());
                }
            }
        } catch (Exception e) {
            m_logger.warn(e);
        }

    }


}
