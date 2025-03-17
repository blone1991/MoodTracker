package com.self.moodtracker.data.network

import com.self.moodtracker.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApiService {
    @GET("advice")
    suspend fun getRandomQuote(): Response<Quote>
}