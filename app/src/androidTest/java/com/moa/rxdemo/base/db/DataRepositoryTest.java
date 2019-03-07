package com.moa.rxdemo.base.db;


import com.moa.rxdemo.App;
import com.moa.rxdemo.db.DataRepository;
import com.moa.rxdemo.db.TestData;
import com.moa.rxdemo.db.entity.Book;
import com.moa.rxdemo.db.entity.User;
import com.moa.rxdemo.db.entity.UserAndBook;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/19 09:49
 */
public class DataRepositoryTest {
    
    private DataRepository repository;
    private List<UserAndBook> userAndBooks;
    private List<Book> books;
    
    @Before
    public void setUp() throws InterruptedException {
        repository = App.getDataRepository();
        userAndBooks = LiveDataTestUtil.getValue(repository.loadUsers());
        books = LiveDataTestUtil.getValue(repository.loadBooks());
    }
    
    @Test
    public void loadUsers() {
        assertTrue(userAndBooks.size() > 0);
    }
    
    @Test
    public void getloadBooks() {
        
        assertTrue(books.size() > 0);
    }
    
    @Test
    public void insertUser() {
        repository.insertUser(TestData.getUser());
    }
    
    @Test
    public void insertBooks() {
        // 需要先执行insertUser
        for (UserAndBook userAndBook : userAndBooks) {
            repository.insertBooks(TestData.getBooks(userAndBook.user.id));
        }
    }
    
    @Test
    public void deleteBook() {
        repository.deleteBook(books.get(0));
    }
    
    @Test
    public void deleteUser() {
        repository.deleteUser(userAndBooks.get(0).user);
    }
    
    @Test
    public void updateUser() {
        User user = userAndBooks.get(0).user;
        user.name = "我是修改过后的名字";
        int number = repository.updateUser(user);
        assertTrue(number >= 1);
    }
    
}