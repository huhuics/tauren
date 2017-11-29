/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tauren.framework.util.ActionUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * 将客户端请求分发给各Controller
 * @author HuHui
 * @version $Id: DispatcherServlet.java, v 0.1 2017年11月24日 下午2:40:38 HuHui Exp $
 */
@WebServlet(value = "/*")
public class DispatcherServlet extends HttpServlet {

    /**  */
    private static final long   serialVersionUID = 3510226343907781443L;

    private static final Logger logger           = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("request uri={}", request.getRequestURI());

        //组装Action Key
        String actionKey = ActionUtil.getActionKey(request.getMethod(), request.getPathInfo());
        Action action = DefaultActionResolver.mapping(actionKey);
        if (action == null) {
            System.out.println("404");
            return;
        }

        ClassUtil.invoke(action.getInstance(), action.getMethod(), null);

    }

}
