package com.moa.rxdemo.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * 此处通过Embedded展开user并通过Relation和book建立关系，
 * 之后对user的select,update和delete操作都会关联到books上面
 * <p>
 * Created by：wangjian on 2018/12/18 16:24
 */
public class UserAndBook {
    
    @Embedded
    public User user;
    
    @Relation(entity = Book.class, parentColumn = "id", entityColumn = "uid")
    public List<Book> books;
   
}
