package com.itg.githubapp.data.network

import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import com.itg.githubapp.data.network.response.UserDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.github.com/"

interface ApiService {


    @GET("repositories")
    fun repositories(
        @Query("since") since: Int
    ): Deferred<Response<List<Repository>>>

    //https://api.github.com/search/repositories?q=tetris
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20
    ): Deferred<Response<SearchReaslt>>

    //https://api.github.com/repos/vanpelt/jsawesome
    @GET("repos/{user_name}/{repo_name}")
    fun repoDetails(
        @Path("user_name") userName: String,
        @Path("repo_name") repoName: String
    ): Deferred<Response<RepoDetails>>

    //https://api.github.com/users/vanpelt
    @GET("users/{user_name}")
    fun userDetails(
        @Path("user_name") userName: String
    ): Deferred<Response<UserDetails>>

    //https://api.github.com/users/hazem3ly/repos
    @GET("users/{user_name}/repos")
    fun userRepos(
        @Path("user_name") userName: String,
        @Query("page") page: Int = 0
    ): Deferred<Response<List<RepoDetails>>>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiService {

            // If I Want To Add Fixed Api Key To All Requests Uncomment this
            /*  val requestInterceptor = Interceptor { chain ->
                  val url = chain.request()
                      .url()
                      .newBuilder()
                      .addQueryParameter("key", API_KEY)
                      .build()
                  val request = chain.request()
                      .newBuilder()
                      .url(url)
                      .build()

                  return@Interceptor chain.proceed(request)
              }
  */
            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL) // TODO Don't Forget To Change BASE_URL
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}