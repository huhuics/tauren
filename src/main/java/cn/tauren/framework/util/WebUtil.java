/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.Constants;
import cn.tauren.framework.enums.RequestMethod;

import com.alibaba.fastjson.JSON;

/**
 * Web工具类
 * @author HuHui
 * @version $Id: WebUtil.java, v 0.1 2017年11月29日 上午9:21:31 HuHui Exp $
 */
public class WebUtil {

    private static final String SPLIT = ":";

    /**
     * 获取Action Key
     * @param method 请求方式{@link RequestMethod}
     * @param uri    请求路径
     * @return       action key
     */
    public static String getActionKey(String method, String uri) {
        StringBuilder sBuilder = new StringBuilder();
        return sBuilder.append(method).append(SPLIT).append(uri).toString();
    }

    /**
     * 跳转到错误页面
     */
    public static void writeError(HttpServletResponse response, int errorCode, String msg) throws IOException {
        response.sendError(errorCode, msg);
    }

    /**
     * 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

    /**
     * 转发
     * @param path jsp文件名
     */
    public static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sBuilder = new StringBuilder(Constants.JSP_PATH_VALUE);
        sBuilder.append(path);
        sBuilder.append(Constants.SUFFIX_JSP);
        String location = sBuilder.toString();
        request.getRequestDispatcher(location).forward(request, response);
    }

    /**
     * 重定向
     */
    public static void redirect(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/" + path);
    }

    /**
     * 向客户端输出json字符串
     * @param obj   要转为json的对象
     */
    public static void writeJson(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=" + Constants.UTF8);
        response.setCharacterEncoding(Constants.UTF8);

        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(obj));
        writer.flush();
    }

}
