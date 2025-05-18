package com.example.demo_gk.repository

import androidx.lifecycle.LiveData
import com.example.demo_gk.CSDL.Book
import com.example.demo_gk.CSDL.BookDao
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookDao: BookDao
) {
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insert(book: Book) = bookDao.insert(book)
    suspend fun update(book: Book) = bookDao.update(book)
    suspend fun delete(book: Book) = bookDao.delete(book)

}
