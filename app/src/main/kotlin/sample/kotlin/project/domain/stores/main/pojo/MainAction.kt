package sample.kotlin.project.domain.stores.main.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action

sealed class MainAction : Action {

    object OnNavigateToFirstScreen : MainAction()

    data class OnConnectivityChanged(val connected: Boolean) : MainAction()
}
