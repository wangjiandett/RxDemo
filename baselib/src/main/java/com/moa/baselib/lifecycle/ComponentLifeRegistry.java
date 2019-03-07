package com.moa.baselib.lifecycle;


import android.app.Application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ComponentLifeRegistry {


    private final List<ComponentApplication> componentApplicationList;

    public ComponentLifeRegistry() {
        componentApplicationList = new ArrayList<>();
    }

    /**
     * 从Manifest文件中查找配置的ComponentApplication
     *
     * @param application
     */
    public void registerFromManifest(Application application) {
        List<ComponentApplication> applicationCollections = ComponentAppResolve.findAllAppLibrary(application);
        for (ComponentApplication component : applicationCollections) {
            if(!componentApplicationList.contains(component)){
                componentApplicationList.add(component);
            }
        }
    }

    public <T extends ComponentApplication> T search(Class<T> clasz) {
        for (ComponentApplication application : componentApplicationList) {
            if (application.getClass().equals(clasz)) {
                return (T) application;
            }
        }
        throw new IllegalStateException(clasz.getSimpleName() + " unregistered!");
    }

    public Collection<ComponentApplication> getAll() {
        return componentApplicationList;
    }

}
