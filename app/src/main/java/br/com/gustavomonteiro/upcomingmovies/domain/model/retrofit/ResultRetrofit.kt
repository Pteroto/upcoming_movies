package br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit

import com.squareup.moshi.Json

data class ResultRetrofit(
    @field:Json(name = "results") val movies: List<MovieRetrofit>
)