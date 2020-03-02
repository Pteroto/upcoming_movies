package br.com.gustavomonteiro.upcomingmovies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.gustavomonteiro.upcomingmovies.R
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.domain.model.RepositoryResponse
import br.com.gustavomonteiro.upcomingmovies.domain.usecase.HomeMovieUseCase
import br.com.gustavomonteiro.upcomingmovies.testutils.CoroutineTestInjection
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var viewModel: HomeViewModel

    @Mock
    lateinit var useCase: HomeMovieUseCase

    @Mock
    lateinit var observerClick: Observer<Int>

    @Mock
    lateinit var observerMovieList: Observer<List<Movie>>

    @Mock
    lateinit var observerErrorMessageString: Observer<String>

    @Mock
    lateinit var observerErrorMessageInt: Observer<Int>

    @Mock
    lateinit var movieList: List<Movie>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun `test if getMovies return a list correctly`() {
        viewModel = HomeViewModel(useCase)

        val repositoryResponseOk = RepositoryResponse.Success(movieList)

        runBlocking {
            viewModel.dispatchers = CoroutineTestInjection.fakeDispatchers
            viewModel.modelViewScope = this

            whenever(useCase.getMovies()).thenReturn(repositoryResponseOk)

            viewModel.movieList.observeForever(observerMovieList)
            viewModel.getMovies()
        }

        verify(observerMovieList).onChanged(movieList)
    }

    @Test
    fun `test if when search, liveData is updated`() {
        viewModel = HomeViewModel(useCase)

        val repositoryResponseOk = RepositoryResponse.Success(movieList)

        runBlocking {
            viewModel.dispatchers = CoroutineTestInjection.fakeDispatchers
            viewModel.modelViewScope = this

            whenever(useCase.getSearchedMovie("a")).thenReturn(repositoryResponseOk)

            viewModel.movieList.observeForever(observerMovieList)
            viewModel.onSearchedMovies("a")
        }

        verify(observerMovieList).onChanged(movieList)
    }

    @Test
    fun `test if the id of onClick is correct on liveData`() {
        viewModel = HomeViewModel(useCase)

        viewModel.onClickListItem(1)
        viewModel.onClickOnMovieOnList.observeForever(observerClick)

        verify(observerClick).onChanged(eq(1))
    }

    @Test
    fun `test if show correctly a message(String) error`() {
        viewModel = HomeViewModel(useCase)

        val repositoryResponseFailureString =
            RepositoryResponse.Error(Failure.FeatureFailure("test"))

        runBlocking {
            viewModel.dispatchers = CoroutineTestInjection.fakeDispatchers
            viewModel.modelViewScope = this

            whenever(useCase.getMovies()).thenReturn(repositoryResponseFailureString)

            viewModel.onErrorMessageString.observeForever(observerErrorMessageString)
            viewModel.getMovies()
        }

        verify(observerErrorMessageString).onChanged("Error: test")
    }

    @Test
    fun `test if show correctly a message(Id) error`() {
        viewModel = HomeViewModel(useCase)

        val repositoryResponseFailureId = RepositoryResponse.Error(Failure.NetworkConnection)

        runBlocking {
            viewModel.dispatchers = CoroutineTestInjection.fakeDispatchers
            viewModel.modelViewScope = this

            whenever(useCase.getMovies()).thenReturn(repositoryResponseFailureId)

            viewModel.onErrorMessageId.observeForever(observerErrorMessageInt)
            viewModel.getMovies()
        }

        verify(observerErrorMessageInt).onChanged(R.string.no_connection_message)
    }
}