package com.example.demo_gk.CSDL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tenSach: String,
    val tacGia: String,
    val ngayXuatBan:String,
    val soTrang: Int
)