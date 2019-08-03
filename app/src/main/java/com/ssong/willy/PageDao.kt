package com.ssong.willy

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface PageDao {
    @Query("SELECT * FROM page")
    fun getAll(): List<Page>

    @Insert(onConflict = REPLACE)
    fun insert(page: Page)

    @Query("SELECT * FROM 'Page' WHERE 'title' = :title")
    fun getPage(title: String): Page

    @Query("DELETE from page")
    fun deleteAll()
}