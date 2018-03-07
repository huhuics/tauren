/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.util;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试集合的元素删除
 * @author HuHui
 * @version $Id: RemoveTest.java, v 0.1 2017年12月27日 上午11:05:36 HuHui Exp $
 */
public class RemoveTest {

    private CopyOnWriteArrayList<Integer>      copyList  = new CopyOnWriteArrayList<Integer>();

    private ArrayList<Integer>                 arrayList = new ArrayList<Integer>();

    private HashMap<Integer, String>           hashMap   = new HashMap<Integer, String>();

    private ConcurrentHashMap<Integer, String> cHashMap  = new ConcurrentHashMap<Integer, String>();

    @Before
    public void init() {

        /*copyList.add(0);
        copyList.add(1);
        copyList.add(2);
        copyList.add(3);
        copyList.add(4);
        copyList.add(5);
        copyList.add(6);*/

        for (int i = 0; i < 7; i++) {
            copyList.add(i);
            arrayList.add(i);
            hashMap.put(i, i + "");
            cHashMap.put(i, i + "");
        }
    }

    /**
     * CopyOnWriteArray增强型迭代
     */
    @Test
    public void testCopyOnWriteArrayList() {

        for (Integer val : copyList) {
            if (val == 3) {
                copyList.remove(3);
            }
        }
    }

    /**
     * ArrayList增强型迭代
     */
    @Test(expected = ConcurrentModificationException.class)
    public void testArrayList() {

        for (Integer val : arrayList) {
            if (val == 3) {
                arrayList.remove(3);
            }
        }
    }

    /**
     * ArrayList使用Iterator迭代
     */
    @Test(expected = ConcurrentModificationException.class)
    public void testArrayList2() {

        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3) {
                arrayList.remove(3);
            }
        }
    }

    /**
     * ArrayList使用Iterator迭代
     * 删除倒数第二个元素
     */
    @Test
    public void testArrayList3() {

        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 5) {
                arrayList.remove(5);
            }
        }
    }

    /**
     * ArrayList使用普通迭代
     */
    @Test
    public void testArrayList4() {

        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 3) {
                arrayList.remove(i);
            }
        }
    }

    /**
     * HashMap使用Iterator迭代
     */
    @Test(expected = ConcurrentModificationException.class)
    public void testHashMap1() {
        Set<Integer> keySet = hashMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3) {
                hashMap.remove(next);
            }
        }
    }

    /**
     * HashMap使用普通迭代
     */
    @Test
    public void testHashMap2() {
        Set<Integer> keySet = hashMap.keySet();

        for (int i = 0; i < keySet.size(); i++) {
            if (i == 3) {
                hashMap.remove(i);
            }
        }
    }

    /**
     * HashMap使用Iterator迭代
     */
    @Test
    public void testHashMap3() {
        Set<Integer> keySet = hashMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3) {
                iterator.remove();
            }
        }
    }

    /**
     * ConcurrentHashMap 使用iterator迭代
     */
    @Test
    public void testConcurrentHashMap() {
        Set<Integer> keySet = cHashMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3) {
                cHashMap.remove(next);
            }
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorMapKey() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 20; i++) {
            map.put(i + "", i + "");
        }

        Iterator<String> it = map.keySet().iterator();
        String key = 10 + "";
        while (it.hasNext()) {
            if (it.next().equals(key)) {
                System.out.println("testIteratorMapKey找到元素：" + key);
                //改变Map
                map.remove(key); //ConcurrentModificationException
                //                map.put(21 + "", 21 + ""); //ConcurrentModificationException
                //                map.replace(key, 30 + "");//正常
                //                it.remove(); //正常
                System.out.println(map.size() + ":" + map.get(key));
            }
        }
    }

}
