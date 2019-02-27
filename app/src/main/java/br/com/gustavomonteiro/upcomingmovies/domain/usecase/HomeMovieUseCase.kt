package br.com.gustavomonteiro.upcomingmovies.domain.usecase

import br.com.gustavomonteiro.upcomingmovies.data.repository.MovieRepositoryRetrofitImpl
import br.com.gustavomonteiro.upcomingmovies.domain.model.ModelContract
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.GenreRetrofit
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.MovieRetrofit
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeMovieUseCase(
    private val moviesRepository: MovieRepositoryRetrofitImpl,
    private val locale: Locale
) : UseCaseContract.MoviesUseCase {

    private lateinit var repositoryGenreResponse: RepositoryResponse<List<ModelContract.RepositoryGenreContract>>

    override suspend fun getMovies(): RepositoryResponse<List<Movie>> {
        repositoryGenreResponse = moviesRepository.getGenres()

        if (repositoryGenreResponse.get() is Failure) {
            return RepositoryResponse.Error(repositoryGenreResponse.get() as Failure)
        }

        val response = moviesRepository.getMovies()

        return if (response.get() is Failure) {
            RepositoryResponse.Error(response.get() as Failure)
        } else {
            @Suppress("UNCHECKED_CAST")
            RepositoryResponse.Success((response.get() as List<MovieRetrofit>).map {
                convertRetrofitModelToUseCaseModel(
                    it
                )
            })
        }
    }

    override suspend fun getSearchedMovie(movieName: String): RepositoryResponse<List<Movie>> {
        repositoryGenreResponse = moviesRepository.getGenres()

        if (repositoryGenreResponse.get() is Failure) {
            return RepositoryResponse.Error(repositoryGenreResponse.get() as Failure)
        }

        val response = moviesRepository.getMovieByName(movieName)

        return if (response.get() is Failure) {
            RepositoryResponse.Error(response.get() as Failure)
        } else {
            @Suppress("UNCHECKED_CAST")
            RepositoryResponse.Success((response.get() as List<MovieRetrofit>).map {
                convertRetrofitModelToUseCaseModel(
                    it
                )
            })
        }
    }

    private fun convertRetrofitModelToUseCaseModel(movieRetrofit: MovieRetrofit): Movie {
        return Movie(
            movieRetrofit.name ?: "",
            movieRetrofit.synopsis ?: "",
            getGenresRetrofit(movieRetrofit.genresIds),
            movieRetrofit.poster ?: "",
            formatDate(movieRetrofit.releaseDate)
        )
    }

    private fun getGenresRetrofit(genresId: List<Int>?): List<String> {
        genresId?.map { genreId ->
            if (repositoryGenreResponse is RepositoryResponse.Success) {
                @Suppress("UNCHECKED_CAST")
                return (repositoryGenreResponse.get() as List<GenreRetrofit>).filter { genreId == it.id.toInt() }
                    .map { it.name }
            }
        }

        return listOf()
    }

    private fun formatDate(date: String?): String {
        if (date == null || date == "") {
            return ""
        }

        DateFormat.getDateInstance()
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", locale)
        val dateParsed = inputFormat.parse(date)
        return DateFormat.getDateInstance(DateFormat.LONG).format(dateParsed)
    }
}