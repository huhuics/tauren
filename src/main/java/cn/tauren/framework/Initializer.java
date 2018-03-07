/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tauren.framework.context.DefaultApplicationContext;

/**
 * 初始化程序，初始化<code>Tauren</code>框架启动所需类
 * @author HuHui
 * @version $Id: Initializer.java, v 0.1 2017年11月28日 下午2:33:19 HuHui Exp $
 */
@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Tauren is init...");
        new DefaultApplicationContext();
        //        registeJspServlet(sce.getServletContext());
        logger.info("Tauren init success!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Tauren destroied");
    }

    /**
     * 添加jsp映射
     */
    @Deprecated
    private void registeJspServlet(ServletContext context) {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        String jspPath = Constants.JSP_PATH_VALUE;
        jspServlet.addMapping(jspPath + "*");
    }

}
