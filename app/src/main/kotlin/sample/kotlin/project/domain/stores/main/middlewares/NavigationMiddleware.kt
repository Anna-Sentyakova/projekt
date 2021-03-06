package sample.kotlin.project.domain.stores.main.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainAction.OnNavigateToFirstScreen
import sample.kotlin.project.domain.stores.main.pojo.MainEvent
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand.NavigateToSplashScreen
import sample.kotlin.project.domain.stores.main.pojo.MainState
import javax.inject.Inject

class NavigationMiddleware
@Inject constructor() : BaseMiddleware<MainState, MainAction, MainEvent, MainNavigationCommand>() {

    override fun bind(
        states: Observable<MainState>,
        actions: Observable<MainAction>,
        events: Consumer<MainEvent>,
        navigationCommands: Consumer<MainNavigationCommand>
    ): Observable<MainAction> =
        actions
            .ofType(OnNavigateToFirstScreen::class.java)
            .doOnNext { navigationCommands.accept(NavigateToSplashScreen) }
            .switchMap { Observable.never<MainAction>() }
}
