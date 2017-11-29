/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.tauren.framework.enums.RequestMethod;
import cn.tauren.framework.mvc.annotation.RequestMapping;
import cn.tauren.framework.util.ActionUtil;
import cn.tauren.framework.util.AssertUtil;

/**
 * 封装URI与Action对应关系
 * @author HuHui
 * @version $Id: DefaultActionResolver.java, v 0.1 2017年11月29日 上午9:05:06 HuHui Exp $
 */
public class DefaultActionResolver {

    private static final String        URI_SUFFIX = "/";

    /**
     * 保存URI和Action的对应关系
     * key: method + ":" + RequestMapping.value()
     * value: Action
     */
    private static Map<String, Action> actionMap  = new ConcurrentHashMap<String, Action>();

    public static void resolve(RequestMapping mapping, Object instance, Method method) {
        Action action = new Action(instance, method, mapping.responseMethod());

        RequestMethod[] requestMethods = mapping.requestMethod();
        for (RequestMethod rMethod : requestMethods) {
            assemble(mapping.value(), rMethod.name(), action);
        }
    }

    public static Action mapping(String actionKey) {
        return actionMap.get(actionKey);
    }

    /**
     * 组装Action Map
     * Action Key = method + ":" + value
     * @param value   客户端标记的URI
     * @param method  
     * @param action
     */
    private static void assemble(String value, String method, Action action) {
        if (value.endsWith(URI_SUFFIX)) {
            value.substring(0, value.length() - 1);
        }
        String actionKey = ActionUtil.getActionKey(method, value);

        AssertUtil.assertTrue(!actionMap.containsKey(actionKey), "request mapping value [" + value + "] has already exists!");

        actionMap.put(actionKey, action);

    }

}
