package com.irontec.axier.irontrivia.networking

import com.irontec.axier.irontrivia.models.TriviaGame
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by axier on 7/4/15.
 */
interface RxTriviaService {

    @GET("api.php")
    fun getQuestions(
            @Query("token") token: String,
            @Query("type") type: String,
            @Query("amount") amount: Int,
            @Query("category") category: Int?,
            @Query("difficulty") difficulty: String
    ): Observable<TriviaGame>

    @GET("api_category.php")
    fun getCategories(): Observable<Response<ResponseBody>>
}
