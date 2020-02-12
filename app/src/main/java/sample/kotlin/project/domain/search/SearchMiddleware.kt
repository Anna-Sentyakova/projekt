package sample.kotlin.project.domain.search

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import sample.kotlin.project.data.Api
import sample.kotlin.project.domain.mvi.Middleware

class SearchMiddleware : Middleware<SearchAction, SearchState, SearchEvent> {

    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    private val api = Api()

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>,
        events: Consumer<SearchEvent>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.SearchClickAction>(SearchAction.SearchClickAction::class.java)
            .switchMap { action ->
                api.search(action.query)
                    .toObservable()
                    .map<SearchAction> { SearchAction.SearchSuccessAction(it) }
                    .doOnError { events.accept(SearchEvent.SearchFailureEvent(it)) }
                    .onErrorReturn { SearchAction.SearchFailureAction(it) }
                    .observeOn(uiScheduler)
                    .startWith(SearchAction.SearchLoadingAction)
            }
}