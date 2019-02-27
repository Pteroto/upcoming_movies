package br.com.gustavomonteiro.upcomingmovies.domain.usecase

import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse

interface UseCaseContract {

    interface MoviesUseCase {
        suspend fun getMovies(): RepositoryResponse<List<Movie>>
        suspend fun getSearchedMovie(movieName: String): RepositoryResponse<List<Movie>>
    }

}