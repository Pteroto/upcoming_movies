package br.com.gustavomonteiro.upcomingmovies.domain.model

import br.com.gustavomonteiro.upcomingmovies.utils.Failure

sealed class RepositoryResponse<out T : Any> {

    data class Success<out T : Any>(val data: T) : RepositoryResponse<T>()
    data class Error(val failure: Failure) : RepositoryResponse<Nothing>()

    fun get(): Any {
        return when (this) {
            is Success<*> -> data
            is Error -> failure
        }
    }
}