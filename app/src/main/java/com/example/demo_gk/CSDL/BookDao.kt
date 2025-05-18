package com.example.demo_gk.CSDL

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY ID DESC")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun intsert(book: Book)

    @Update
    suspend fun upadate(book: Book)

    @Delete
    suspend fun delete(book: Book)
}