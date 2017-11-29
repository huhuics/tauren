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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tauren.framework.Constants;
import cn.tauren.framework.util.ClassUtil;
import cn.tauren.framework.util.WebUtil;

/**
 * 将客户端请求分发给各Controller
 * @author HuHui
 * @version $Id: DispatcherServlet.java, v 0.1 2017年11月24日 下午2:40:38 HuHui Exp $
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    /** uid */
    private static final long   serialVersionUID = 3510226343907781443L;

    private static final Logger logger           = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(Constants.UTF8);

        String requestPath = WebUtil.getRequestPath(request);
        logger.info("request path={}", requestPath);

        if (StringUtils.equals(requestPath, "/")) {
            WebUtil.redirect(Constants.INDEX, request, response);
            return;
        }

        //组装Action Key
        String actionKey = WebUtil.getActionKey(request.getMethod(), requestPath);
        Action action = DefaultActionResolver.mapping(actionKey);

        if (action == null) {
            WebUtil.writeError(response, HttpServletResponse.SC_NOT_FOUND, "页面不存在");
            return;
        }

        String result = invoke(action, request);

        WebUtil.forward(result, request, response);

    }

    /**
     * 调用Controller方法
     */
    private String invoke(Action action, HttpServletRequest request) {
        return (String) ClassUtil.invoke(action.getInstance(), action.getMethod(), new Object[] { request });
    }

}
