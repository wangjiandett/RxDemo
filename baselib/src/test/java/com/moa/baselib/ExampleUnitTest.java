package com.moa.baselib;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //assertEquals(4, 2 + 2);

        Properties properties = new Properties();
        System.out.println("user.name : " + System.getProperty("user.name"));

        System.out.println("user.home : " + System.getProperty("user.home"));

        System.out.println("user.dir : " + System.getProperty("user.dir"));

        System.out.println(new File(System.getProperty("user.dir")).getParentFile().getAbsolutePath());


        String path = new File(System.getProperty("user.dir")).getParentFile().getAbsolutePath() + "/gradle.properties";
        try {
            properties.load(new FileInputStream(new File(path)));

            System.out.println(properties.getProperty("isLibrary"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==================="+isLibrary());

        System.out.println("package: " + Test.class.getPackage().getName());

        System.out.println("package: " + Test.class.getPackage().toString());
    }

    public Properties getGradleProperty(String propertyFileName){
        Properties properties = new Properties();
        // 当前项目的目录 H:\work\RxDemo2\baselib
        File baselibDir = new File(System.getProperty("user.dir"));
        // 项目跟目录 H:\work\RxDemo2
        File projectDir = baselibDir.getParentFile();
        if(projectDir != null){
            String path = projectDir.getAbsolutePath()+"/"+propertyFileName;
            File file = new File(path);
            if (file.exists()){
                try {
                    properties.load(new FileInputStream(file));
                    return properties;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public boolean isLibrary(){
        Properties properties = getGradleProperty("gradle.properties");
        if(properties != null){
            String islib = properties.getProperty("isLibrary");
            return "true".equals(islib);
        }

        return false;
    }
}