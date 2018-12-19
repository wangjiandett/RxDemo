package com.moa.rxdemo.base.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * 创建books表
 * <p>
 * Created by：wangjian on 2018/12/19 14:05
 */
@Entity(tableName = "books", foreignKeys = {
    @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns ="uid",
        onDelete = ForeignKey.CASCADE)})
public class Book {
    @PrimaryKey
    @NonNull
    public String bookid;
    public String uid;
    public String bookname;
    public String date;
    public String author;
    public String desc;
}