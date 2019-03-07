package com.moa.rxdemo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * 创建users表
 * <p>
 * Created by：wangjian on 2018/12/19 14:05
 */
@Entity(tableName = "users")
public class User {
    public String name;
    public String address;
    public String phone;
    @PrimaryKey
    @NonNull
    public String id;
}
