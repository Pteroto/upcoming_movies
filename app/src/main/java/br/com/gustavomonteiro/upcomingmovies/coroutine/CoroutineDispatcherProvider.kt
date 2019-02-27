package br.com.gustavomonteiro.upcomingmovies.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutineDispatcherProvider(
    val ui: CoroutineDispatcher = Dispatchers.Main,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val computation: CoroutineDispatcher = Dispatchers.Default
)