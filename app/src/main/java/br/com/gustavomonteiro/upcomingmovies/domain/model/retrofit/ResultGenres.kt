package br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit

import com.squareup.moshi.Json

data class ResultGenres(
    @field:Json(name = "genres") val genreRetrofits: List<GenreRetrofit>
)