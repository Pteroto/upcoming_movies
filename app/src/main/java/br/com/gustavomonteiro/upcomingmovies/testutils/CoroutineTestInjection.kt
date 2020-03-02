package br.com.gustavomonteiro.upcomingmovies.testutils

import br.com.gustavomonteiro.upcomingmovies.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.Dispatchers

object CoroutineTestInjection {

    @Suppress("EXPERIMENTAL_API_USAGE")

    val fakeDispatchers = CoroutineDispatcherProvider(
        Dispatchers.Unconfined,
        Dispatchers.Unconfined,
        Dispatchers.Unconfined
    )

}