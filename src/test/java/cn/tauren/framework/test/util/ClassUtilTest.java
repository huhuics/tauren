/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.util;

import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.test.ClassroomService;
import cn.tauren.framework.test.StudentService;
import cn.tauren.framework.test.StudentServiceImpl;
import cn.tauren.framework.util.ClassUtil;

/**
 * 
 * @author HuHui
 * @version $Id: ClassUtilTest.java, v 0.1 2017年11月16日 下午8:38:20 HuHui Exp $
 */
public class ClassUtilTest {

    @Test
    public void test() {
        String str1 = "StudentService";
        String ret1 = ClassUtil.humpNaming(str1);
        Assert.assertTrue(StringUtils.equals(ret1, "studentService"));

        String str2 = "A";
        String ret2 = ClassUtil.humpNaming(str2);
        Assert.assertTrue(StringUtils.equals(ret2, "a"));

        String str3 = "b";
        String ret3 = ClassUtil.humpNaming(str3);
        Assert.assertTrue(StringUtils.equals(ret3, "b"));

    }

    @Test
    public void testAssign() {
        boolean ret = StudentService.class.isAssignableFrom(StudentServiceImpl.class);
        Assert.assertTrue(ret);
    }

    @Test
    public void testModifier() {
        boolean isAbs = Modifier.isAbstract(AbsClass.class.getModifiers());
        System.out.println(isAbs);

        isAbs = Modifier.isAbstract(ClassUtil.class.getModifiers());
        System.out.println(isAbs);
    }

    @Test
    public void testForname() throws Exception {
        Class<?> clazz = ClassUtils.getClass("cn.tauren.framework.test.util.ClassUtilTest.AbClass", false);
        Assert.assertNotNull(clazz);
    }

    @Test
    public void testInstance() {
        ClassroomService cs = new ClassroomService();
        Assert.assertTrue(ClassroomService.class.isInstance(cs));
    }

    @Test
    public void testNullInstance() {
        Assert.assertTrue(Null.class.isInstance(ObjectUtils.NULL));
    }

    class AbClass {
        public AbClass(int i) {
            System.out.println("i=" + i);
        }
    }

    abstract class AbsClass {

    }

}
