/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.exception;

/**
 * 创建Bean过程中如果发生异常则抛出此异常
 * @author HuHui
 * @version $Id: BeanCreationException.java, v 0.1 2017年11月20日 下午8:49:19 HuHui Exp $
 */
public class BeanCreationException extends BeanException {

    /**  */
    private static final long serialVersionUID = 5523643305638647024L;

    public BeanCreationException() {
        super();
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(Throwable cause) {
        super(cause);
    }

}
