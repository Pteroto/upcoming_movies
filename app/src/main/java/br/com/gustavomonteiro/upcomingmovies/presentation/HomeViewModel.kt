package br.com.gustavomonteiro.upcomingmovies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gustavomonteiro.upcomingmovies.R
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie
import br.com.gustavomonteiro.upcomingmovies.domain.usecase.HomeMovieUseCase
import br.com.gustavomonteiro.upcomingmovies.utils.Failure
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val homeMovieUseCase: HomeMovieUseCase) : BaseViewModel() {

    private val internalListMovie = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = internalListMovie

    private val internalMovieId = MutableLiveData<Int>()
    val onClickOnMovieOnList: LiveData<Int>
        get() = internalMovieId

    private val internalOnErrorMessageString = MutableLiveData<String>()
    val onErrorMessageString: LiveData<String>
        get() = internalOnErrorMessageString

    private val internalOnErrorMessageId = MutableLiveData<Int>()
    val onErrorMessageId: LiveData<Int>
        get() = internalOnErrorMessageId

    fun getMovies() {
        modelViewScope.launch(dispatchers.computation) {
            val result = homeMovieUseCase.getMovies()

            withContext(dispatchers.ui) {
                if (result.get() is Failure) {
                    val failure = result.get() as Failure
                    setOnErrorMessage(failure)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    val movieList = result.get() as List<Movie>
                    internalListMovie.postValue(movieList)
                }
            }
        }
    }

    fun onSearchedMovies(movieName: String) {
        modelViewScope.launch(dispatchers.computation) {
            val result = homeMovieUseCase.getSearchedMovie(movieName)

            withContext(dispatchers.ui) {
                if (result.get() is Failure) {
                    val failure = result.get() as Failure
                    setOnErrorMessage(failure)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    val movieList = result.get() as List<Movie>
                    internalListMovie.postValue(movieList)
                }
            }
        }
    }

    private fun setOnErrorMessage(failure: Failure) {
        if (failure is Failure.NetworkConnection) {
            internalOnErrorMessageId.postValue(R.string.no_connection_message)
            return
        } else if (failure is Failure.FeatureFailure) {
            internalOnErrorMessageString.postValue("Error: ${failure.errorMessage}")
            return
        }

        internalOnErrorMessageId.postValue(R.string.unknown_error_message)
    }

    fun onClickListItem(id: Int) {
        internalMovieId.postValue(id)
    }
}