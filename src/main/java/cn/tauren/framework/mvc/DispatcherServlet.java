/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将客户端请求分发给各Controller
 * @author HuHui
 * @version $Id: DispatcherServlet.java, v 0.1 2017年11月24日 下午2:40:38 HuHui Exp $
 */
public class DispatcherServlet extends HttpServlet {

    /**  */
    private static final long   serialVersionUID = 3510226343907781443L;

    private static final Logger logger           = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info("DispatchServlet收到请求");
    }

}
