package sample.kotlin.project.domain.providers.connectivity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.logging.utils.DeviceUtils

@Suppress("deprecation")
val ConnectivityManager.networkConnected
    get() =
        if (DeviceUtils.isAtLeastMarshmallow()) {
            val capabilities = getNetworkCapabilities(activeNetwork)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false ||
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
        } else {
            activeNetworkInfo?.isConnected ?: false
        }
