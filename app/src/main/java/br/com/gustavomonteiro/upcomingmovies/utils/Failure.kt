package br.com.gustavomonteiro.upcomingmovies.utils

sealed class Failure {
    object NetworkConnection : Failure()

    class FeatureFailure(val errorMessage: String) : Failure()
}