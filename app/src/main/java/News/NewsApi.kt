package News

import News.Utils.Companion.APIKEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("v2/top-headlines")


    fun getNews(
        @Query("category") category: String,
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String
    ): Call<NewsData>




}