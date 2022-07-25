package com.mkavaktech.readingtrackers.core.init.network

import com.mkavaktech.readingtrackers.core.init.model.BookModel
import com.mkavaktech.readingtrackers.core.init.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): BookModel

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}