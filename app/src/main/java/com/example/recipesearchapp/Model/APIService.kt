package com.example.recipesearchapp.Model

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class QueryParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url

        val urlBuilder = originalHttpUrl.newBuilder()

        // Clear existing query parameters(This is for removing the parameters from the URL if they are null)
        urlBuilder.removeAllEncodedQueryParameters("cuisine")
        urlBuilder.removeAllEncodedQueryParameters("excludeIngredients")
        urlBuilder.removeAllEncodedQueryParameters("minProtein")
        urlBuilder.removeAllEncodedQueryParameters("maxCarbs")


        // Rebuild the URL to remove empty query parameters
        for (i in 0 until originalHttpUrl.querySize) {
            val name = originalHttpUrl.queryParameterName(i)
            val value = originalHttpUrl.queryParameterValue(i)
            if (!value.isNullOrEmpty()) {
                urlBuilder.addQueryParameter(name, value)
            }
        }

        val url = urlBuilder.build()
        val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(QueryParameterInterceptor())
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.spoonacular.com/recipes/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface APIService {
    @GET("complexSearch?apiKey=fb8346755d6a4b9c908518a3d99fa382")
    suspend fun getcategories(): ResultResponse

    @GET("random?number=25&veryPopular=true&apiKey=fb8346755d6a4b9c908518a3d99fa382")
    suspend fun getpopularrecipe(): RecipeResponse

    @GET("{id}/information?includeNutrition=true&apiKey=fb8346755d6a4b9c908518a3d99fa382")
    suspend fun getRecipeInfo(
        @Path("id") id: String
    ): RecipeDetailResponse
//    If the part we want to change in the URL string is after the ? then we use @Query otherwise we use @Path

    @GET("complexSearch")
    suspend fun getSearchResults(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String? = null,  //This is because except for query the other parameters are supposed to be optional so their default value is set to null
        @Query("excludeIngredients") excludeIngredients: String? = null,
        @Query("minProtein") minProtein: String? = null,
        @Query("maxCarbs") maxCarbs: String? = null,
        @Query("apiKey") apiKey: String = "fb8346755d6a4b9c908518a3d99fa382"
    ): SearchResponse

    @GET("{id}/similar?apiKey=fb8346755d6a4b9c908518a3d99fa382")
    suspend fun getSimillarRecipe(
        @Path("id") id:String
    ): List<SimillarRecipe>
}

val allrecipeservice = retrofit.create(APIService::class.java)