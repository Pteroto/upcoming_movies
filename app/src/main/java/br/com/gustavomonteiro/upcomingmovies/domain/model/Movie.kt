package br.com.gustavomonteiro.upcomingmovies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val synopsis: String,
    val genres: List<String>,
    val poster: String,
    val releaseDate: String
) : Parcelable