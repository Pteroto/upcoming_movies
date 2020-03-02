package br.com.gustavomonteiro.upcomingmovies.repository

import br.com.gustavomonteiro.upcomingmovies.data.network.TMDbApi
import br.com.gustavomonteiro.upcomingmovies.data.repository.ApiBuilder
import br.com.gustavomonteiro.upcomingmovies.data.repository.MovieRepositoryRetrofitImpl
import br.com.gustavomonteiro.upcomingmovies.data.repository.RepositoryContract
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse
import br.com.gustavomonteiro.upcomingmovies.testutils.runTesting
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class MovieRepositoryRetrofitImplTest {

    @Mock
    lateinit var networkHandler: RepositoryContract.NetworkHandler

    @Mock
    lateinit var api: TMDbApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `getMovies is returning error without network connection`() = runTesting {
        whenever(networkHandler.isConnected).thenReturn(false)
        val repository = MovieRepositoryRetrofitImpl(api, networkHandler, Locale.getDefault())
        val response = repository.getMovies()
        response shouldEqual RepositoryResponse.Error(Failure.NetworkConnection)
    }
}