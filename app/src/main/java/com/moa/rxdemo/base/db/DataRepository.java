package com.moa.rxdemo.base.db;

import android.arch.lifecycle.LiveData;

import com.moa.rxdemo.base.db.entity.Book;
import com.moa.rxdemo.base.db.entity.Student;
import com.moa.rxdemo.base.db.entity.User;
import com.moa.rxdemo.base.db.entity.UserAndBook;
import com.moa.rxdemo.base.dispatcher.Runtimes;

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
    
    public LiveData<List<UserAndBook>> loadUsers() {
        return mDatabase.userAndBookDao().loadUsers();
    }
    
    public LiveData<List<Book>> loadBooks() {
        return mDatabase.userAndBookDao().loadBooks();
    }
    
    public void insertUser(final User user){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().insetUser(user);
            }
        });
    }
    
    public void insertBooks(final List<Book> books){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().insetBooks(books);
            }
        });
    }
    
    public void deleteUser(final User user){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().deleteUser(user);
            }
        });
    }
    
    public void deleteBook(final Book book){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().deleteBook(book);
            }
        });
    }
    
    public int updateUser(User user){
       return mDatabase.userAndBookDao().updateUser(user);
    }
    
    public void updateBook(final Book book){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().updateBook(book);
            }
        });
    }
    
    
    public LiveData<List<Student>> loadStudents() {
        return mDatabase.userAndBookDao().loadStudents();
    }
    
    public void insertStudent(final Student user){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().insertStudent(user);
            }
        });
    }
    
    public void deleteStudent(final Student book){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.userAndBookDao().deleteStudent(book);
            }
        });
    }
}
