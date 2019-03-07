package com.moa.rxdemo.db;

import android.arch.lifecycle.LiveData;
import android.database.Cursor;

import com.moa.baselib.base.dispatcher.Runtimes;
import com.moa.rxdemo.db.entity.Book;
import com.moa.rxdemo.db.entity.Student;
import com.moa.rxdemo.db.entity.User;
import com.moa.rxdemo.db.entity.UserAndBook;

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
    
   //==========================以下封装数据库具体操作=======================
    
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
        return mDatabase.studentDao().loadStudents();
    }
    
    public void insertStudent(final Student user){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.studentDao().insertStudent(user);
            }
        });
    }
    
    public void deleteStudent(final Student book){
        Runtimes.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.studentDao().deleteStudent(book);
            }
        });
    }
    
    //======================以下测试provider的使用=======================
    
    public Cursor loadStudentsCursor(){
        return mDatabase.studentDao().loadStudentsCursor();
    }
    
    public Cursor getStudentById(String bookid){
        return mDatabase.studentDao().getStudentById(bookid);
    }
    
    public long insertStudent2(final Student user){
        return mDatabase.studentDao().insertStudent2(user);
    }
    
    public long[] insertAllStudent(final Student[] students){
        return mDatabase.studentDao().insertAllStudent(students);
    }
    
    public int deleteStudent2(final String bookid){
        return mDatabase.studentDao().deleteStudent2(bookid);
    }
    
    public int updateStudent2(final Student user){
        return mDatabase.studentDao().updateStudent2(user);
    }
    
    
    
    
}
