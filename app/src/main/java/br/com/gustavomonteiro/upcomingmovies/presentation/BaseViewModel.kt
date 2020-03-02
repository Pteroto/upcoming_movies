package br.com.gustavomonteiro.upcomingmovies.presentation

import androidx.lifecycle.ViewModel
import br.com.gustavomonteiro.upcomingmovies.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    final override val coroutineContext: CoroutineContext
        get() = Job()

    var modelViewScope: CoroutineScope = GlobalScope.plus(coroutineContext)
    var dispatchers = CoroutineDispatcherProvider()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()

        modelViewScope.cancel()
    }
}