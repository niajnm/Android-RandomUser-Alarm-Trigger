package News

import com.example.randomuser.APIClient
import com.example.randomuser.AccessTokenServiceInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofitnews {
    private const val BASE_URL = "https://newsapi.org/"
    private var retrofit: Retrofit? = null
    private val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }

//    fun getClient(): Retrofit {
//        return retrofit
//    }
    @JvmStatic
    fun getClient(): NewsApi {
        return client!!.create(
            NewsApi::class.java
        )
    }
}