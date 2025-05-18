package com.example.demo_gk.di

import android.content.Context
import androidx.room.Room
import com.example.demo_gk.CSDL.AppDatabase
import com.example.demo_gk.CSDL.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "book_db"
        ).build()
    }

    @Provides
    fun provideBookDao(db: AppDatabase): BookDao = db.bookData()
}