package br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit

import br.com.gustavomonteiro.upcomingmovies.domain.model.ModelContract
import com.squareup.moshi.Json

data class GenreRetrofit(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "id") val id: String
) : ModelContract.RepositoryGenreContract