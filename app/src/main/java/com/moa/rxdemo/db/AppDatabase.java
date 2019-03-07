/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moa.rxdemo.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.moa.rxdemo.db.dao.StudentDao;
import com.moa.rxdemo.db.dao.UserAndBookDao;
import com.moa.rxdemo.db.entity.Book;
import com.moa.rxdemo.db.entity.Student;
import com.moa.rxdemo.db.entity.User;


@Database(entities = {User.class, Book.class, Student.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    
    private static AppDatabase sInstance;
    
    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample.db";
    
    public abstract UserAndBookDao userAndBookDao();
    
    public abstract StudentDao studentDao();
    
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    
    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                AppDatabase.getInstance(appContext).setDatabaseCreated();
            }
        }).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build();
    }
    
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }
    
    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }
    
    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
    
    /**
     * 数据库升级使用
     */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        
        }
    };
    
    /**
     * 2-3的时候添加了一张新表student数据库升级使用
     * 在gradle配置导出schema后，编译后会自动生成shenma对应的版本（gradle中做了相应的配置），之后可以copy schema中的创建语句
     */
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS student "
                + "(`bookid` TEXT NOT NULL, `uid` TEXT, `bookname` TEXT, "
                + "`date` TEXT, `author` TEXT, `desc` TEXT, PRIMARY KEY(`bookid`))");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // CREATE INDEX index_name ON table_name (column_list)
            // DROP INDEX index_name ON talbe_name
            database.execSQL("CREATE INDEX 'index_books_uid' ON books(`uid`)");
        }
    };
}
