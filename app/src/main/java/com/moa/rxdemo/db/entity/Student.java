package com.moa.rxdemo.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 创建books表
 * <p>
 * Created by：wangjian on 2018/12/19 14:05
 */
@Entity(tableName = Student.TABLE_NAME)
public class Student {
    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "student";
    
    public static final String COLUMN_BOOKID = "bookid";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_BOOKNAME = "bookname";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DESC = "desc";
    
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_BOOKID)
    public String bookid;
    @ColumnInfo(name = COLUMN_UID)
    public String uid;
    @ColumnInfo(name = COLUMN_BOOKNAME)
    public String bookname;
    @ColumnInfo(name = COLUMN_DATE)
    public String date;
    @ColumnInfo(name = COLUMN_AUTHOR)
    public String author;
    @ColumnInfo(name = COLUMN_DESC)
    public String desc;
    
    public static Student fromContentValues(ContentValues values) {
        final Student student = new Student();
        if (values.containsKey(COLUMN_BOOKID)) {
            student.bookid = values.getAsString(COLUMN_BOOKID);
        }
        if (values.containsKey(COLUMN_UID)) {
            student.uid = values.getAsString(COLUMN_UID);
        }
        if (values.containsKey(COLUMN_BOOKNAME)) {
            student.bookname = values.getAsString(COLUMN_BOOKNAME);
        }
        if (values.containsKey(COLUMN_DATE)) {
            student.date = values.getAsString(COLUMN_DATE);
        }
        if (values.containsKey(COLUMN_AUTHOR)) {
            student.author = values.getAsString(COLUMN_AUTHOR);
        }
        if (values.containsKey(COLUMN_DESC)) {
            student.desc = values.getAsString(COLUMN_DESC);
        }
        
        return student;
    }
    
    public static ContentValues toContentValues(Student student){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKID, student.bookid);
        values.put(COLUMN_UID, student.uid);
        values.put(COLUMN_BOOKNAME, student.bookname);
        values.put(COLUMN_DATE, student.date);
        values.put(COLUMN_AUTHOR, student.author);
        values.put(COLUMN_DESC, student.desc);
        return values;
    }
    
    
    
    @Override
    public String toString() {
        return "bookid: "+bookid+
            "\nuid: "+uid+
            "\nbookname: "+bookname+
            "\ndate: "+date+
            "\nauthor: "+author+
            "\ndesc: "+desc;
    }
}