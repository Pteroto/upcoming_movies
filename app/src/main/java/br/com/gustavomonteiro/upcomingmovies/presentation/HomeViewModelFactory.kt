package br.com.gustavomonteiro.upcomingmovies.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.gustavomonteiro.upcomingmovies.data.device.NetworkHandler
import br.com.gustavomonteiro.upcomingmovies.data.network.TMDbApi
import br.com.gustavomonteiro.upcomingmovies.data.repository.ApiBuilder
import br.com.gustavomonteiro.upcomingmovies.data.repository.MovieRepositoryRetrofitImpl
import br.com.gustavomonteiro.upcomingmovies.domain.usecase.HomeMovieUseCase
import java.util.*

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(getHomeUseCase()) as T
    }

    private fun getHomeUseCase(): HomeMovieUseCase {
        return HomeMovieUseCase(getRepository(), Locale.getDefault())
    }

    private fun getRepository(): MovieRepositoryRetrofitImpl {
        return MovieRepositoryRetrofitImpl(getRetrofitApi(), getNetworkHandler(), Locale.getDefault())
    }

    private fun getRetrofitApi(): TMDbApi{
        return ApiBuilder().createApi()
    }

    private fun getNetworkHandler(): NetworkHandler {
        return NetworkHandler(context)
    }
}