package com.alamin.testme.model.network

import com.alamin.testme.model.data.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("api.php")
    suspend fun questions(
        @Query("amount") amount:Int,
        @Query("category") categoryId:Int,
        @Query("difficulty") difficulty:String,
        @Query("type") type:String
    ):Response<QuestionResponse>
}