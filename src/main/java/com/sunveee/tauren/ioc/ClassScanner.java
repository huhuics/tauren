package com.sunveee.tauren.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * 类扫描器
 * 
 * @author 51Sun
 * @version $Id: ClassScanner.java, v 0.1 2017年11月30日 下午10:50:17 51Sun Exp $
 */
public class ClassScanner {
    
    private String pkgPath;
    
    private List<Class<?>>      classList       = new ArrayList<Class<?>>();
    
    public ClassScanner(String pkgPath){
        this.pkgPath = pkgPath;
    }

}
