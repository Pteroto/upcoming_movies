package br.com.gustavomonteiro.upcomingmovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

val Context.networkInfo: NetworkInfo? get() =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo