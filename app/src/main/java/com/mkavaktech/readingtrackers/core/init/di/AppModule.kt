package com.mkavaktech.readingtrackers.core.init.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mkavaktech.readingtrackers.core.constants.network.NetworkConstants
import com.mkavaktech.readingtrackers.core.init.network.NetworkService
import com.mkavaktech.readingtrackers.core.init.repository.FireRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(
        queryBook = FirebaseFirestore.getInstance()
            .collection("books")
    )

    @Singleton
    @Provides
    fun provideBookApi(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}