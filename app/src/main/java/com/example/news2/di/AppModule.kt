package com.example.news2.di

import android.content.Context
import androidx.room.Room
import com.example.news2.data.api.ApiNews
import com.example.news2.data.db.ArticleDatabase
import com.example.news2.data.repository.NewsRepositoryImp
import com.example.news2.domain.repository.ArticleRepository
import com.example.news2.util.Constants
import com.example.news2.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNewsApi(): ApiNews = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiNews::class.java)

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: ApiNews, db: ArticleDatabase, @ApplicationContext app: Context): ArticleRepository = NewsRepositoryImp(db,api, app)


    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, ArticleDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun getArticleDao(db: ArticleDatabase) = db.getDaoSearchNews()
}