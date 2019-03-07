package com.moa.rxdemo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * 封装对表的操作
 * <p>
 * Created by：wangjian on 2018/12/18 17:36
 */
@Dao
public interface UserAndBookDao {
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetUser(com.moa.rxdemo.db.entity.User user);
    
    @Delete
    void deleteUser(com.moa.rxdemo.db.entity.User user);
    
    @Update
    int updateUser(com.moa.rxdemo.db.entity.User user);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetBooks(List<com.moa.rxdemo.db.entity.Book> books);
    
    @Delete
    void deleteBook(com.moa.rxdemo.db.entity.Book book);
    
    @Update
    void updateBook(com.moa.rxdemo.db.entity.Book book);
    
    @Query("SELECT * FROM users")
    @Transaction
    LiveData<List<com.moa.rxdemo.db.entity.UserAndBook>> loadUsers();
    
    @Query("SELECT * FROM books")
    LiveData<List<com.moa.rxdemo.db.entity.Book>> loadBooks();
}
