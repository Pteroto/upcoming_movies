package br.com.gustavomonteiro.upcomingmovies.data.network

import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.ResultGenres
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.ResultRetrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApi {

    @GET("search/movie")
    fun getMovieByName(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") text: String
    ): Call<ResultRetrofit>

    @GET("movie/upcoming")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResultRetrofit>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String
    ): Call<ResultGenres>
}