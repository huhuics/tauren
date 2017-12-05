/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import cn.tauren.framework.orm.annotation.Transaction;

/**
 * 事务代理
 * <p>拦截一个类中被{@link Transaction}标注的方法，并生成其事务代理</p>
 * @author HuHui
 * @version $Id: TransactionPartialInterceptor.java, v 0.1 2017年12月5日 下午7:47:36 HuHui Exp $
 */
public class TransactionPartialInterceptor extends TransactionInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T newProxyInstance(final Class<T> targetClass, final Object target) {
        return (T) Enhancer.create(targetClass, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /**
                 * 如果没有@Transaction，则直接调用原方法
                 */
                if (!method.isAnnotationPresent(Transaction.class)) {
                    return method.invoke(target, args);
                }

                before(targetClass, method, args);
                Object ret = null;
                try {
                    ret = method.invoke(target, args);
                } catch (Exception e) {
                    exception(targetClass, method, args, e);
                }
                after(targetClass, method, args);
                return ret;
            }
        });
    }

}
