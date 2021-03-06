package sample.kotlin.project.presentation.core.views

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.presentation.core.views.extensions.unexpectedError

abstract class BaseViewModel<S : State, A : Action, E : Event, NC : NavigationCommand>
constructor(
    private val store: Store<S, A, E, NC>
) : ViewModel() {

    final override fun toString() = super.toString()
    val logger: Logger = LoggerFactory.getLogger(toString())

    internal val statesObservable = store.statesObservable
    internal val eventsHolder = store.eventsHolder
    private val navigationCommandsObservable = store.navigationCommandsObservable
    private val disposables = CompositeDisposable()

    init {
        disposables += navigationCommandsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handle, ::unexpectedError)
    }

    protected open fun handle(navigationCommand: NC) {
        // override in nested classes if needed
    }

    override fun onCleared() {
        logger.debug("cleared view model $this")
        store.dispose()
        disposables.clear()
    }

    internal fun dispatch(action: A) {
        store.dispatch(action)
    }
}
