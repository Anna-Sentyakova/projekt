package sample.kotlin.project.domain.stores.main.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.network.NetworkConnectivityHelper
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState
import javax.inject.Inject

class NetworkConnectivityMiddleware
@Inject constructor(
    private val networkConnectivityHelper: NetworkConnectivityHelper
) : Middleware<MainState, MainAction, MainEvent, MainNavigationCommand> {

    override fun bind(
        states: Observable<MainState>,
        actions: Observable<MainAction>,
        events: Consumer<MainEvent>,
        navigationCommands: Consumer<MainNavigationCommand>
    ): Observable<MainAction> =
        actions
            .ofType<MainAction.NetworkConnectedChanged>(
                MainAction.NetworkConnectedChanged::class.java
            )
            .doOnNext { networkConnectivityHelper.setNetworkConnected(it.isConnected) }
            .switchMap { Observable.never<MainAction>() }
}