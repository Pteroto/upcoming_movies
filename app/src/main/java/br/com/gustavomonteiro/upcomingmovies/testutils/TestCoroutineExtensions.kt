package br.com.gustavomonteiro.upcomingmovies.testutils

import kotlinx.coroutines.runBlocking

fun runTesting(f: suspend () -> Unit) = runBlocking {
    f()
    Unit
}