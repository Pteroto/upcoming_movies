package br.com.gustavomonteiro.upcomingmovies.data.repository

import br.com.gustavomonteiro.upcomingmovies.data.network.ConfigValues
import br.com.gustavomonteiro.upcomingmovies.data.network.TMDbApi
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.GenreRetrofit
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.MovieRetrofit
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.ResultRetrofit
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import retrofit2.Response
import java.io.IOException
import java.util.*

class MovieRepositoryRetrofitImpl(
    private val api: TMDbApi,
    private val networkHandler: RepositoryContract.NetworkHandler,
    private val locale: Locale
) :
    RepositoryContract.MoviesRepository {

    override suspend fun getMovies(): RepositoryResponse<List<MovieRetrofit>> {
        if (!networkHandler.isConnected) {
            return RepositoryResponse.Error(Failure.NetworkConnection)
        }

        try {
            val result: Response<ResultRetrofit> = api.getMovies(ConfigValues.ApiKey, locale.toLanguageTag(), 1).execute()

            return if (result.isSuccessful) {
                if (result.body() != null) {
                    RepositoryResponse.Success(result.body()!!.movies)
                } else {
                    RepositoryResponse.Error(Failure.FeatureFailure("Body null"))
                }
            } else {
                RepositoryResponse.Error(Failure.FeatureFailure(result.code().toString()))
            }
        } catch (ioException: IOException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(ioException.toString()))
        } catch (runtimeException: RuntimeException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(runtimeException.toString()))
        }
    }

    override suspend fun getMovieByName(name: String): RepositoryResponse<List<MovieRetrofit>> {
        if (!networkHandler.isConnected) {
            return RepositoryResponse.Error(Failure.NetworkConnection)
        }

        try {
            val result: Response<ResultRetrofit> =
                api.getMovieByName(ConfigValues.ApiKey, locale.toLanguageTag(), 1, name).execute()

            return if (result.isSuccessful) {
                if (result.body() != null) {
                    RepositoryResponse.Success(result.body()!!.movies)
                } else {
                    RepositoryResponse.Error(Failure.FeatureFailure("Body null"))
                }
            } else {
                RepositoryResponse.Error(Failure.FeatureFailure(result.code().toString()))
            }
        } catch (ioException: IOException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(ioException.toString()))
        } catch (runtimeException: RuntimeException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(runtimeException.toString()))
        }
    }

    override suspend fun getGenres(): RepositoryResponse<List<GenreRetrofit>> {
        if (!networkHandler.isConnected) {
            return RepositoryResponse.Error(Failure.NetworkConnection)
        }

        try {
            val result = api.getGenres(ConfigValues.ApiKey).execute()

            return if (result.isSuccessful) {
                if (result.body() != null) {
                    RepositoryResponse.Success(result.body()!!.genreRetrofits)
                } else {
                    RepositoryResponse.Error(Failure.FeatureFailure("Body null"))
                }
            } else {
                RepositoryResponse.Error(Failure.FeatureFailure(result.code().toString()))
            }
        } catch (ioException: IOException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(ioException.toString()))
        } catch (runtimeException: RuntimeException) {
            return RepositoryResponse.Error(Failure.FeatureFailure(runtimeException.toString()))
        }
    }
}