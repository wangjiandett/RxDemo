package com.moa.baselib.lifecycle;

import java.util.Comparator;

/**
 * 对ComponentApplication进行排序
 */
public class ComponentLevelComparator implements Comparator<ComponentApplication> {

    @Override
    public int compare(ComponentApplication o1, ComponentApplication o2) {
        return o1.level() - o2.level();
    }
}
