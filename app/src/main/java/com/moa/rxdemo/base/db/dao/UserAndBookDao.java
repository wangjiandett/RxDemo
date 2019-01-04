package com.moa.rxdemo.base.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.moa.rxdemo.base.db.entity.Book;
import com.moa.rxdemo.base.db.entity.User;
import com.moa.rxdemo.base.db.entity.UserAndBook;

import java.util.List;

/**
 * 封装对表的操作
 * <p>
 * Created by：wangjian on 2018/12/18 17:36
 */
@Dao
public interface UserAndBookDao {
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetUser(User user);
    
    @Delete
    void deleteUser(User user);
    
    @Update
    int updateUser(User user);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetBooks(List<Book> books);
    
    @Delete
    void deleteBook(Book book);
    
    @Update
    void updateBook(Book book);
    
    @Query("SELECT * FROM users")
    LiveData<List<UserAndBook>> loadUsers();
    
    @Query("SELECT * FROM books")
    LiveData<List<Book>> loadBooks();
}
