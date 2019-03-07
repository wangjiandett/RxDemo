package com.moa.rxdemo.base.db;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.moa.baselib.utils.Randoms;
import com.moa.rxdemo.db.DataContentProvicer;
import com.moa.rxdemo.db.TestData;
import com.moa.rxdemo.db.entity.Student;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/28 15:31
 */
public class DataContentProvicerTest {
    
    ContentResolver contentResolver;
    
    @Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getTargetContext();
        contentResolver = context.getContentResolver();
    }
    
    @Test
    public void query() {
        Cursor cursor = contentResolver.query(DataContentProvicer.URI_STUDENT, null, null, null, null);
        
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String bookid = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_BOOKID));
            String uid = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_UID));
            String bookname = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_BOOKNAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_DATE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_DESC));
            
            System.out.println("bookid:" + bookid);
        }
    }
    
    @Test
    public void delete() {
        Student student = TestData.getStudent(Randoms.randomInt() + "");
        Uri uri = contentResolver.insert(DataContentProvicer.URI_STUDENT, Student.toContentValues(student));
        int id2 = contentResolver.delete(uri, null, null);
        assertEquals(id2, 1);
    }
    
    @Test
    public void update() {
        Student student = TestData.getStudent(Randoms.randomInt() + "");
        Uri uri = contentResolver.insert(DataContentProvicer.URI_STUDENT, Student.toContentValues(student));
        
        // Uri uriUpdate = Uri.withAppendedPath(DataContentProvicer.URI_STUDENT, student.bookid);
        student.author = "eeeeeeeeeeeeee 2 xxxxxxxx";
        int value = contentResolver.update(DataContentProvicer.URI_STUDENT_ITEM, Student.toContentValues(student), null,
            null);
        assertEquals(1, value);
    }
    
    
    @Test
    public void insert() {
        
        Student student = TestData.getStudent(Randoms.randomInt() + "");
        Uri uri = contentResolver.insert(DataContentProvicer.URI_STUDENT, Student.toContentValues(student));
        long id = ContentUris.parseId(uri);
        
        Cursor cursor = contentResolver.query(DataContentProvicer.URI_STUDENT, null, null, null, null);
        System.out.println("count:" + cursor.getCount() + ", id:" + id);
    }
    
    @Test
    public void bulkInsert() {
        
        // 测试批量插入
        ContentValues[] values = new ContentValues[10];
        
        for (int i = 0; i < 10; i++) {
            Student student = TestData.getStudent(Randoms.randomInt() + i + "");
            values[i] = Student.toContentValues(student);
        }
        
        int length = contentResolver.bulkInsert(DataContentProvicer.URI_STUDENT, values);
        
        Cursor cursor = contentResolver.query(DataContentProvicer.URI_STUDENT, null, null, null, null);
        System.out.println("count:" + cursor.getCount() + ", length:" + length);
    }
    
    @Test
    public void applyBatch() {
        
        // 测试批量操作
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        Student student = TestData.getStudent(Randoms.randomInt() + "");
        // 1插入操作
        operations.add(ContentProviderOperation.newInsert(DataContentProvicer.URI_STUDENT)
            .withValues(Student.toContentValues(student)).build());
        
        student = TestData.getStudent(Randoms.randomInt() + "");
        // 2插入操作
        operations.add(ContentProviderOperation.newInsert(DataContentProvicer.URI_STUDENT)
            .withValues(Student.toContentValues(student)).build());
    
        student = TestData.getStudent(Randoms.randomInt() + "");
        // 3插入操作
        operations.add(ContentProviderOperation.newInsert(DataContentProvicer.URI_STUDENT)
            .withValues(Student.toContentValues(student)).build());
        
        // 4删除操作
        operations.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(DataContentProvicer
            .URI_STUDENT_ITEM, Long.parseLong(student.bookid))).build());
        
        try {
            contentResolver.applyBatch(DataContentProvicer.AUTHORITY, operations);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}