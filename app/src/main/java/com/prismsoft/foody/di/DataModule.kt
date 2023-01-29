package com.prismsoft.foody.di

import com.prismsoft.foody.data.RemoteDataSource
import com.prismsoft.foody.data.Repository
import com.prismsoft.foody.network.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatasource(foodRecipesApi: FoodRecipesApi): RemoteDataSource =
        RemoteDataSource(foodRecipesApi)

    @Singleton
    @Provides
    fun provideRepository(dataSource: RemoteDataSource): Repository =
        Repository(dataSource)
}