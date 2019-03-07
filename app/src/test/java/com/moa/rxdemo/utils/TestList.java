package com.moa.rxdemo.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestList {

    @Test
    public void testRemove() {
        List<String> strings = new ArrayList<>();
        strings.add("11111");
        strings.add("11112");
        strings.add("11113");
        strings.add("11114");
        System.out.println(Arrays.toString(strings.toArray()));
//        for (String s : strings){
//            strings.remove(s);
//        }

        // 动态删除集合中的数据 防止出现数据越界
        // 使用动态获取size()函数，strings.size()
//        for (int i = 0; i < strings.size() -1; i++) {
//            strings.remove(i);
//        }

        // 使用迭代器 遍历会动态更新index
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            iterator.remove();
        }

        System.out.println(Arrays.toString(strings.toArray()));
    }

    @Test
    public void testCountDownLatch() {
        System.out.println(getResult());
    }


    private String getResult() {
        int size = 5;
        // 阻塞线程等待所有线程执行完
        final CountDownLatch countDownLatch = new CountDownLatch(size);

        final String[] result = {""};

        for (int i = 0; i < size; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    result[0] = result[0] + finalI + ", ";
                    countDownLatch.countDown();
                    System.out.println(result[0]);
                }
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }

}
