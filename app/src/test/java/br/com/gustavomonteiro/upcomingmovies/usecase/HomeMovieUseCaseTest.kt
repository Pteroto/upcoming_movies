package br.com.gustavomonteiro.upcomingmovies.usecase

import br.com.gustavomonteiro.upcomingmovies.data.network.TMDbApi
import br.com.gustavomonteiro.upcomingmovies.data.repository.MovieRepositoryRetrofitImpl
import br.com.gustavomonteiro.upcomingmovies.data.repository.RepositoryContract
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.GenreRetrofit
import br.com.gustavomonteiro.upcomingmovies.domain.model.retrofit.MovieRetrofit
import br.com.gustavomonteiro.upcomingmovies.domain.usecase.HomeMovieUseCase
import br.com.gustavomonteiro.upcomingmovies.testutils.runTesting
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class HomeMovieUseCaseTest {

    @Mock
    lateinit var genreRetrofitList: List<GenreRetrofit>

    @Mock
    lateinit var repository: MovieRepositoryRetrofitImpl

    @Mock
    lateinit var api: TMDbApi

    @Mock
    lateinit var featureFailure: Failure.FeatureFailure

    @Mock
    lateinit var networkHandler: RepositoryContract.NetworkHandler

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test if return Error(network connection) without internet connection`() = runTesting {
        whenever(networkHandler.isConnected).thenReturn(false)

        val repository = MovieRepositoryRetrofitImpl(api, networkHandler, Locale.getDefault())
        val useCase = HomeMovieUseCase(repository, Locale.getDefault())
        val result = useCase.getMovies()
        result shouldEqual RepositoryResponse.Error(Failure.NetworkConnection)
    }

    @Test
    fun `test if return Error(feature failure) with half api malformed`() = runTesting {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(repository.getMovies()).thenReturn(RepositoryResponse.Error(featureFailure))
        whenever(repository.getGenres()).thenReturn(RepositoryResponse.Success(genreRetrofitList))
        val useCase = HomeMovieUseCase(repository, Locale.getDefault())
        val result = useCase.getMovies()
        result shouldEqual RepositoryResponse.Error(featureFailure)
    }

    @Test
    fun `test if return parsed movie correctly`() = runTesting {
        Locale.setDefault(Locale("en", "US"))

        whenever(networkHandler.isConnected).thenReturn(true)
        val genreList = listOf(GenreRetrofit("test1", "1"), GenreRetrofit("test2", "2"))
        val movieList = listOf(MovieRetrofit("movieName", "synopsesText", listOf(1, 2), "2018-11-16", "fakeUrl"))

        whenever(repository.getGenres()).thenReturn(RepositoryResponse.Success(genreList))
        whenever(repository.getMovies()).thenReturn(RepositoryResponse.Success(movieList))

        val useCase = HomeMovieUseCase(repository, Locale.getDefault())
        val result = useCase.getMovies()

        val movie = ((result.get() as List<*>)[0] as Movie)

        movie.name shouldEqual movieList[0].name
        movie.releaseDate shouldEqual "November 16, 2018"
        movie.genres[0] shouldEqual genreList[0].name
    }

}