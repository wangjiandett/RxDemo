package com.moa.rxdemo.base.db

import com.moa.rxdemo.base.db.entity.Book
import com.moa.rxdemo.base.db.entity.Student
import com.moa.rxdemo.base.db.entity.User
import com.moa.rxdemo.utils.DateFormatting
import com.moa.rxdemo.utils.Randoms
import java.util.*

/**
 * 封装测试数据
 *
 * Created by：wangjian on 2018/12/19 09:51
 */
object TestData {

    fun getUser(): User {
        val randomStr = Randoms.randomInt().toString();

        val user = User();
        user.address = "北京市日本镇日本村${randomStr}号"
        user.id = randomStr
        user.name = "小白${randomStr}号"
        user.phone = "1500000000$randomStr"

        return user;
    }

    fun getBook(uid: String): Book {
        val randomStr = Randoms.randomInt().toString();
        val book = Book();
        book.bookid = randomStr;
        book.uid = uid;
        book.bookname = "钓鱼岛是中国的$randomStr";
        book.date = DateFormatting.formatDate(Date().time);
        book.author = "小黑$randomStr";
        book.desc = "台湾岛也是中国的$randomStr";

        return book;
    }

    fun getBooks(uid: String): List<Book>{
        val items = mutableListOf<Book>()
        val size = Math.random()*10 + 10;
        for (item in 0..size.toInt()) {
            items.add(getBook(uid))
        }

        return items;
    }

    fun getStudent(uid: String): Student {
        val randomStr = Randoms.randomInt().toString();
        val student = Student();
        student.bookid = randomStr;
        student.uid = uid;
        student.bookname = "钓鱼岛是中国的$randomStr";
        student.date = DateFormatting.formatDate(Date().time);
        student.author = "小黑$randomStr";
        student.desc = "台湾岛也是中国的$randomStr";

        return student;
    }
}
