package com.sunveee.tauren.ioc;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器
 * 
 * @author 51Sun
 * @version $Id: ClassScanner.java, v 0.1 2017年11月30日 下午10:50:17 51Sun Exp $
 */
public interface ClassScanner {

    public List<Class<?>> getClassList();

    public List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> anno);

}
