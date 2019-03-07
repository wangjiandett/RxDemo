package com.moa.rxdemo.db;

import com.moa.baselib.utils.DateFormatting;
import com.moa.baselib.utils.Randoms;
import com.moa.rxdemo.db.entity.Book;
import com.moa.rxdemo.db.entity.Student;
import com.moa.rxdemo.db.entity.User;

import java.util.ArrayList;
import java.util.List;

public class TestData {


    public static User getUser() {

        User user = new User();
        user.address = "北京市日本镇日本村${randomStr}号";
        user.id = Randoms.randomInt() + "";
        user.name = "小白${randomStr}号";
        user.phone = "1500000000$randomStr";
        return user;
    }

    public static Book getBook(String uid) {

       Book book = new Book();
       book.bookid = Randoms.randomInt() + "";
       book.uid = uid;
       book.bookname = "钓鱼岛是中国的$randomStr";
       book.date = DateFormatting.formatDate(System.currentTimeMillis());
       book.author = "小黑$randomStr";

        return book;
    }

    public static List<Book> getBooks(String uid) {

        List<Book> items = new ArrayList<>();
        int size = (int) (Math.random()*10 + 10);
        for (int i =0;i < size;i ++){
            items.add(getBook(uid));
        }

        return items;
    }


    public static Student getStudent(String uid)

    {
        String randomStr = Randoms.randomInt()+"";
        Student student = new Student();
        student.bookid = randomStr;
        student.uid = uid;
        student.bookname = "钓鱼岛是中国的$randomStr";
        student.date = DateFormatting.formatFull(System.currentTimeMillis());
        student.author = "小黑$randomStr";
        student.desc = "台湾岛也是中国的$randomStr";

        return student;
    }
}
