package com.ssong.willy

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Page")
class Page (@PrimaryKey var id: Long?,
        @ColumnInfo(name = "title") var title: String?,
        @ColumnInfo(name = "content") var content: String?
){
    constructor(): this(null, "", "")
}