package br.com.gustavomonteiro.upcomingmovies.data.device

import android.content.Context
import br.com.gustavomonteiro.upcomingmovies.data.repository.RepositoryContract
import br.com.gustavomonteiro.upcomingmovies.utils.networkInfo

open class NetworkHandler(context: Context) : RepositoryContract.NetworkHandler {

    private val appContext = context.applicationContext

    @Suppress("DEPRECATION")
    override val isConnected
        get() = appContext.networkInfo?.isConnectedOrConnecting ?: false
}