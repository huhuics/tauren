/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.exception;

/**
 * 
 * @author HuHui
 * @version $Id: BeanException.java, v 0.1 2017年11月20日 下午8:07:52 HuHui Exp $
 */
public abstract class BeanException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = -892763896248527875L;

    public BeanException() {
        super();
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }

}
