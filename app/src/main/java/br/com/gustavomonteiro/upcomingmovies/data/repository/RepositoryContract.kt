package br.com.gustavomonteiro.upcomingmovies.data.repository

import br.com.gustavomonteiro.upcomingmovies.domain.model.ModelContract.RepositoryGenreContract
import br.com.gustavomonteiro.upcomingmovies.domain.model.ModelContract.RepositoryMovieContract
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse

interface RepositoryContract {

    interface MoviesRepository {
        suspend fun getMovies(): RepositoryResponse<List<RepositoryMovieContract>>
        suspend fun getMovieByName(name: String): RepositoryResponse<List<RepositoryMovieContract>>
        suspend fun getGenres(): RepositoryResponse<List<RepositoryGenreContract>>
    }

    interface NetworkHandler {
        val isConnected: Boolean
    }
}