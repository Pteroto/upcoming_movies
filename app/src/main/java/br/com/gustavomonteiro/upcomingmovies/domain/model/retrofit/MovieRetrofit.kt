package br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit

import br.com.gustavomonteiro.upcomingmovies.domain.model.ModelContract
import com.squareup.moshi.Json

data class MovieRetrofit(
    @field:Json(name = "original_title") val name: String?,
    @field:Json(name = "overview") val synopsis: String?,
    @field:Json(name = "genre_ids") val genresIds: List<Int>?,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "poster_path") val poster: String?
) : ModelContract.RepositoryMovieContract