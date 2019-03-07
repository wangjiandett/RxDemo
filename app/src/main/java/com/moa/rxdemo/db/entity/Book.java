package com.moa.rxdemo.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
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
        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "uid")}
        )
public class Book {
    @PrimaryKey
    @NonNull
    public String bookid;
    @ColumnInfo(name = "uid")
    public String uid;
    public String bookname;
    public String date;
    public String author;
    public String desc;
    
    @Override
    public String toString() {
        return "bookid:"+bookid+
            "uid:"+uid+
            "bookname:"+bookname+
            "date:"+date+
            "author:"+author+
            "desc:"+desc;
    }
}