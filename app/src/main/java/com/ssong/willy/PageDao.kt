package com.ssong.willy

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface PageDao {
    @Query("SELECT * FROM page")
    fun getAll(): List<Page>

    @Insert(onConflict = REPLACE)
    fun insert(page: Page)

    @Insert(onConflict = IGNORE)
    fun initialInsert(page: Page)

    //This made my DB invisible.. now I fixed it, but before that
    //I changed every usage of 'getPage' function to 'getAll()' function.
    //And still getPage function is not working properly?
    @Query("SELECT * FROM Page WHERE title = :title")
    fun getPage(title: String): Page

    @Query("SELECT * FROM Page WHERE id = :id")
    fun getPage(id: Int): Page

    @Query("DELETE from page")
    fun deleteAll()
}