package com.moa.rxdemo.db;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moa.rxdemo.App;
import com.moa.rxdemo.db.entity.Student;

import java.util.ArrayList;

/**
 * 演示provider中使用room，测试用例参考DataContentProvicerTest
 * <br/>
 * Created by：wangjian on 2018/12/27 17:53
 */
public class DataContentProvicer extends ContentProvider {
    
    public static final String AUTHORITY = "con.moa.rxdemo.DataContentProvider";
    
    /**
     * The URI for the student table.
     */
    public static final Uri URI_STUDENT = Uri.parse("content://" + AUTHORITY + "/" + Student.TABLE_NAME);
    
    public static final Uri URI_STUDENT_ITEM = Uri.parse("content://" + AUTHORITY + "/" + Student.TABLE_NAME + "/#");
    
    private static final int CODE_ITEM = 1;
    private static final int CODE_DIR = 2;
    
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    
    static {
        uriMatcher.addURI(AUTHORITY, Student.TABLE_NAME, CODE_DIR);
        uriMatcher.addURI(AUTHORITY, Student.TABLE_NAME + "/#", CODE_ITEM);// /* 匹配字符串，/# 匹配数字
    }
    
    @Override
    public boolean onCreate() {
        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        
        if (code == CODE_ITEM || code == CODE_DIR) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            DataRepository repository = App.getDataRepository();
            
            final Cursor cursor;
            if (code == CODE_DIR) {
                cursor = repository.loadStudentsCursor();
            }
            else {
                cursor = repository.getStudentById(String.valueOf(ContentUris.parseId(uri)));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        }
        else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CODE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Student.TABLE_NAME;
            case CODE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Student.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CODE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = App.getDataRepository().deleteStudent2(
                    String.valueOf(ContentUris.parseId(uri)));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CODE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Student student = Student.fromContentValues(values);
                final int count = App.getDataRepository().updateStudent2(student);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        
        switch (uriMatcher.match(uri)) {
            case CODE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                
                DataRepository repository = App.getDataRepository();
                Student student = Student.fromContentValues(values);
                
                repository.insertStudent2(student);
                context.getContentResolver().notifyChange(uri, null);
                return Uri.withAppendedPath(uri, student.uid);
            case CODE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        
        switch (uriMatcher.match(uri)) {
            case CODE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                
                DataRepository repository = App.getDataRepository();
                Student[] students = new Student[values.length];
                for (int i = 0; i < values.length; i++) {
                    students[i] = Student.fromContentValues(values[i]);
                }
                
                // 批量插入
                int length = repository.insertAllStudent(students).length;
                context.getContentResolver().notifyChange(uri, null);
                return length;
        }
        
        return super.bulkInsert(uri, values);
    }
    
    @Override
    public ContentProviderResult[] applyBatch(
        ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        // 封装事物回滚操作
        App.getAppDatabase().beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            App.getAppDatabase().setTransactionSuccessful();
            return result;
        } finally {
            App.getAppDatabase().endTransaction();
        }
    }
}
