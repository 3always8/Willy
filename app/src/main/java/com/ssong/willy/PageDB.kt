package com.ssong.willy

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Page::class], version = 1)
abstract class PageDB: RoomDatabase() {
    abstract fun pageDao(): PageDao

    companion object {
        private var INSTANCE: PageDB? = null

        fun getInstance(context: Context): PageDB? {
            if (INSTANCE == null) {
                synchronized(PageDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PageDB::class.java, "cat.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}