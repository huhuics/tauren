/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.exception;

/**
 * 当没有此Bean时抛出该异常
 * @author HuHui
 * @version $Id: NoSuchBeanException.java, v 0.1 2017年11月20日 下午8:09:55 HuHui Exp $
 */
public class NoSuchBeanException extends BeanException {

    /**  */
    private static final long serialVersionUID = 5437449124219328484L;

    public NoSuchBeanException() {
        super();
    }

    public NoSuchBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(Throwable cause) {
        super(cause);
    }

}
