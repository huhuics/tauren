/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;


/**
 * Bean注入器的接口
 * @author HuHui
 * @version $Id: NameInjector.java, v 0.1 2017年11月20日 下午7:17:31 HuHui Exp $
 */
public interface BeanInjector {

    /**
     * 返回被注入的对象实例
     */
    void inject();

}
