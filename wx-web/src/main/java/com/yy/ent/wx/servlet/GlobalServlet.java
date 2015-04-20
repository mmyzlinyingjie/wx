package com.yy.ent.wx.servlet;

import com.yy.ent.cherry.v2.Cherry;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class GlobalServlet extends HttpServlet {

    private static final long serialVersionUID = -4840151180036401261L;
    static Logger m_logger = Logger.getLogger(GlobalServlet.class);

    @Override
    public void init() throws ServletException {
        String configURL = this.getServletContext().getRealPath("WEB-INF/conf/log4j.properties");
        PropertyConfigurator.configure(configURL);
        try {
            String cherryConfig = this.getServletContext().getRealPath("WEB-INF/conf/cherry.xml");
            m_logger.info("cherryConfig:" + cherryConfig);
            Cherry cherry = new Cherry(cherryConfig);
            cherry.init();

            //TODO 表达式  Schedule

//            // 静态化
//            String url = this.getServletContext().getRealPath("WEB-INF/conf/htmlized.xml");
//            Htmlized htmlized = new Htmlized(url);

//            htmlized.start();
            
        } catch (Exception e) {
            m_logger.error("GlobalServlet init Cherry Plugin init Fail.", e);
            throw new ServletException("GlobalServlet init Cherry Plugin init Fail.", e);
        }
    }

}
