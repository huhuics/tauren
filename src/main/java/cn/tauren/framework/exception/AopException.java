/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.exception;

/**
 * 
 * @author HuHui
 * @version $Id: AopException.java, v 0.1 2017年11月22日 上午10:34:26 HuHui Exp $
 */
public class AopException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 2138137344111254926L;

    public AopException() {
        super();
    }

    public AopException(String message, Throwable cause) {
        super(message, cause);
    }

    public AopException(String message) {
        super(message);
    }

    public AopException(Throwable cause) {
        super(cause);
    }

}
