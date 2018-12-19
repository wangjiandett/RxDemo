package com.moa.rxdemo.base.db;

import android.arch.lifecycle.LiveData;

import com.moa.rxdemo.base.db.entity.Book;
import com.moa.rxdemo.base.db.entity.User;
import com.moa.rxdemo.base.db.entity.UserAndBook;

import java.util.List;


/**
 * Repository handling the work with UserAndBookDao
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    LiveData<List<UserAndBook>> loadUsers() {
        return mDatabase.userAndBookDao().loadUsers();
    }

    LiveData<List<Book>> loadBooks() {
        return mDatabase.userAndBookDao().loadBooks();
    }
    
    void insertUser(User user){
        mDatabase.userAndBookDao().insetUser(user);
    }
    
    void insertBooks(List<Book> books){
        mDatabase.userAndBookDao().insetBooks(books);
    }
    
    void deleteUser(User user){
        mDatabase.userAndBookDao().deleteUser(user);
    }
    
    void deleteBook(Book book){
        mDatabase.userAndBookDao().deleteBook(book);
    }
    
    int updateUser(User user){
       return mDatabase.userAndBookDao().updateUser(user);
    }
    
    void updateBook(Book book){
        mDatabase.userAndBookDao().updateBook(book);
    }
    
}
