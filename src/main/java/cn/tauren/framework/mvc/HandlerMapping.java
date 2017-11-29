/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc;

/**
 * 处理映射
 * @author HuHui
 * @version $Id: HandlerMapping.java, v 0.1 2017年11月28日 下午12:40:06 HuHui Exp $
 */
public class HandlerMapping {

    /** 请求路径 */
    private String url;

    /** 请求路径对应的Action */
    private Action action;

    public HandlerMapping() {
    }

    public HandlerMapping(String url, Action action) {
        this.url = url;
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
