package com.example.demo_gk.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_gk.CSDL.Book
import com.example.demo_gk.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val respository: BookRepository
):ViewModel(){

    val allBooks: LiveData<List<Book>> = respository.allBooks

    fun insert(book: Book) = viewModelScope.launch {
        respository.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch {
        respository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch {
        respository.delete(book)
    }
};