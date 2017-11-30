package com.sunveee.tauren.ioc.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.sunveee.tauren.ioc.ClassScanner;
import com.sunveee.tauren.util.AssertUtil;

/**
 * 类扫描器实现
 * 
 * @author 51
 * @version $Id: DefaultClassScanner.java, v 0.1 2017年12月1日 上午11:10:34 51 Exp $
 */
public class DefaultClassScanner implements ClassScanner {

    private static final String  FILE_PROTOCOL = "file";

    private static final String  FILE_SUFFIX   = ".class";

    private String               pkgName;

    private final List<Class<?>> classList     = new ArrayList<Class<?>>();

    private Object               lock          = new Object();

    public DefaultClassScanner(String pkgName) {
        this.pkgName = pkgName;
    }

    @Override
    public List<Class<?>> getClassList() {
        AssertUtil.assertNotBlank(pkgName, "包路径不能为空");
        synchronized (lock) {
            if (CollectionUtils.isNotEmpty(classList)) {
                return classList;
            }
            // 将包名中的'.'替换为'/'
            String pkgDir = pkgName.replace('.', '/');
            // 定义一个枚举集合
            Enumeration<URL> dirs;
            try {
                dirs = Thread.currentThread().getContextClassLoader().getResources(pkgDir);
                while (dirs.hasMoreElements()) {
                    URL url = dirs.nextElement();
                    String protocol = url.getProtocol();
                    if (StringUtils.equals(FILE_PROTOCOL, protocol)) { //如果是文件
                        // 获取包的物理路径
                        String pkgPath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.displayName());
                        // 扫描包下的文件并放入列表
                        _addClassesToList(pkgName, pkgPath, classList);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("扫描类失败", e);
            }

            return classList;
        }
    }

    @Override
    public List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> anno) {
        List<Class<?>> classListByAnno = new ArrayList<Class<?>>();
        List<Class<?>> classList = getClassList();
        if (CollectionUtils.isNotEmpty(classList)) {
            for (Class<?> _clazz : classList) {
                if (_clazz.isAnnotationPresent(anno)) {
                    // 获取所有被指定注解修饰的class
                    classListByAnno.add(_clazz);
                }
            }
        }

        return classListByAnno;
    }

    /**
     * 递归遍历
     * @param pkgName  包名,用于拼接类全限定名以获取Class对象
     * @param pkgPath  包的物理路径,用于扫描当前路径下的.class文件以及目录
     * @param classList  类的集合
     */
    private void _addClassesToList(String pkgName, String pkgPath, List<Class<?>> classList) {
        File pkgFile = new File(pkgPath);

        // 如果文件不存在或不是目录则直接返回
        if (!pkgFile.exists() || !pkgFile.isDirectory()) {
            return;
        }

        // 此时pkgPath是一个目录，获取该目录下所有.class文件和目录
        File[] files = pkgFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(FILE_SUFFIX);
            }
        });

        for (File file : files) {
            if (file.isDirectory()) { // 如果是目录则递归遍历
                String subPkgName = pkgName + '.' + file.getName();
                _addClassesToList(subPkgName, file.getAbsolutePath(), classList);
            } else { // 处理class文件
                String className = file.getName().substring(0, file.getName().length() - 6);//去除.class后缀
                String classFullName = pkgName + '.' + className;
                try {
                    classList.add(ClassUtils.getClass(classFullName, false));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("无法找到类", e);
                }
            }
        }
    }

}
