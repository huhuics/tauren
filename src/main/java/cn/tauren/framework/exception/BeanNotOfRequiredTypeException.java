/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.exception;

/**
 * 当查找的Bean与传入的Bean Type类型不同时抛出此异常
 * @author HuHui
 * @version $Id: BeanNotOfRequiredTypeException.java, v 0.1 2017年11月20日 下午8:10:54 HuHui Exp $
 */
public class BeanNotOfRequiredTypeException extends BeanException {

    /**  */
    private static final long serialVersionUID = -572819797341477680L;

    public BeanNotOfRequiredTypeException() {
        super();
    }

    public BeanNotOfRequiredTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanNotOfRequiredTypeException(String message) {
        super(message);
    }

    public BeanNotOfRequiredTypeException(Throwable cause) {
        super(cause);
    }

}
