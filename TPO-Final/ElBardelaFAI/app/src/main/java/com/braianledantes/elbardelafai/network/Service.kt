package com.braianledantes.elbardelafai.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ElBarDeLaFaiService {
    @POST("api/drinks")
    suspend fun createDrink(
        @Body drink: NetworkDrink
    ): NetworkDrink

    @PATCH("api/drinks/{id}")
    suspend fun updateDrink(
        @Body drink: NetworkDrink,
        @Path("id") idDrink: String = drink.id
    ): NetworkDrink

    @GET("api/drinks/{id}")
    suspend fun getDrink(
        @Path("id") id: String
    ): NetworkDrink

    @GET("api/drinks")
    suspend fun getDrinkList(
        @Query("search") search: String,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): NetworkDrinkListContainer

    @GET("api/drinks/concurrents")
    suspend fun getCurrentDrinkList(): List<NetworkDrink>

    @GET("api/ingredients")
    suspend fun getIngredientsList(
        @Query("search") search: String,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): NetworkIngredientListContainer

    @GET("api/ingredients/{name}")
    suspend fun getIngredientByName(
        @Path("name") name: String
    ) : NetworkIngredient
}

object ElBarDeLaFaiNetwork {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://elbardelafai-dev-adzk.3.us-1.fl0.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ElBarDeLaFaiService::class.java)

}


