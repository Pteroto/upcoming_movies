package br.com.gustavomonteiro.upcomingmovies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Intent
import br.com.gustavomonteiro.upcomingmovies.domain.model.Movie

class DetailsViewModel : BaseViewModel() {

    companion object {
        const val KEY_MOVIE_OBJECT = "KEY_MOVIE_OBJECT"
    }

    private val internalMovieObject = MutableLiveData<Movie>()
    val onLoadedData: LiveData<Movie>
        get() = internalMovieObject

    private val internalOnError = MutableLiveData<Unit>()
    val onLoadError: LiveData<Unit>
        get() = internalOnError

    fun loadData(intent: Intent?) {
        intent?.let {
            it.getParcelableExtra<Movie>(KEY_MOVIE_OBJECT)?.let { movie ->
                internalMovieObject.postValue(movie)
                return
            }
        }

        internalOnError.postValue(Unit)
    }
}