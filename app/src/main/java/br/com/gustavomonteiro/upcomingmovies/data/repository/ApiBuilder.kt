package br.com.gustavomonteiro.upcomingmovies.data.repository

import br.com.gustavomonteiro.upcomingmovies.data.network.TMDbApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiBuilder {
    private val baseURL = "https://api.themoviedb.org/3/"

    fun createApi(): TMDbApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(TMDbApi::class.java)
    }
}