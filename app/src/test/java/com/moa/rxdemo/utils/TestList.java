package com.moa.rxdemo.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestList {

    @Test
    public void testRemove(){
        List<String> strings = new ArrayList<>();
        strings.add("11111");
        strings.add("11112");
        strings.add("11113");
        strings.add("11114");
        System.out.println(Arrays.toString(strings.toArray()));
//        for (String s : strings){
//            strings.remove(s);
//        }

//        for (int i = 0; i < strings.size() -1; i++) {
//            strings.remove(i);
//        }

        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            iterator.remove();
        }

        System.out.println(Arrays.toString(strings.toArray()));

    }

}
