package com.example.demo_gk.CSDL

import androidx.lifecycle.LiveData
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookDao: BookDao
) {
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insert(book: Book) = bookDao.intsert(book)
    suspend fun update(book: Book) = bookDao.upadate(book)
    suspend fun delete(book: Book) = bookDao.delete(book)

}
