package com.moa.rxdemo.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moa.baselib.utils.DateFormatting
import com.moa.rxdemo.db.entity.Book
import org.junit.Test
import java.util.*

/**
 * 测试DateFormatting中的方法
 *
 * Created by：wangjian on 2018/12/18 11:02
 */
class DateFormattingTest {

    @Test
    fun formatTime() {
        // 由于使用了android的类，需要单独验证
        // println(DateFormatting.formatTime(System.currentTimeMillis()))
    }

    @Test
    fun formatDate() {
        println(DateFormatting.formatDate(System.currentTimeMillis()))
    }

    @Test
    fun formatFull(){
        println(DateFormatting.formatFull(System.currentTimeMillis()))
    }

    @Test
    fun areSameDays(){
        val date1 = Date.parse("2017/2/12")
        val date2 = Date.parse("2017/2/12")

        println(DateFormatting.areSameDays(date1, date2))
    }

    @Test
    fun testGson(){
        val gson = GsonBuilder().serializeNulls().create()
        val book : Book = Book()
        book.bookid = "xxxx"

        val str = gson.toJson(book);

        println(str)
        val b = Gson().fromJson(str, Book::class.java)
        requireNotNull(b.author)

    }

}