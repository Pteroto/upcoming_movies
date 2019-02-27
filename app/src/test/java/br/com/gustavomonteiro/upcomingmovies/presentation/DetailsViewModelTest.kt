package br.com.gustavomonteiro.upcomingmovies.presentation

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.presentation.DetailsViewModel
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerError: Observer<Unit>

    @Mock
    lateinit var observerMovie: Observer<Movie>

    lateinit var viewModel: DetailsViewModel

    @Mock
    lateinit var intent: Intent

    @Mock
    lateinit var movie: Movie

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailsViewModel()
    }

    @Test
    fun `test if intent correctly return ok`() {
        whenever(intent.getParcelableExtra<Movie>(DetailsViewModel.KEY_MOVIE_OBJECT))
            .thenReturn(movie)

        viewModel.loadData(intent)
        viewModel.onLoadedData.observeForever(observerMovie)

        verify(observerMovie).onChanged(eq(movie))
    }

    @Test
    fun `test if intent null return error`() {
        viewModel.loadData(null)
        viewModel.onLoadError.observeForever(observerError)

        verify(observerError).onChanged(Unit)
    }

    @Test
    fun `test if extra null return error`() {
        whenever(intent.getParcelableExtra<Movie>(DetailsViewModel.KEY_MOVIE_OBJECT))
            .thenReturn(null)

        viewModel.loadData(intent)
        viewModel.onLoadError.observeForever(observerError)

        verify(observerError).onChanged(Unit)
    }
}