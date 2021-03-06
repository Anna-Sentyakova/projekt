package sample.kotlin.project.presentation.core.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.logging.LogSystem.sens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.MviView
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.presentation.core.views.extensions.unexpectedError
import javax.inject.Inject

@Suppress("TooManyFunctions")
abstract class BaseFragment<S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>> :
    Fragment(), HasAndroidInjector, MviView<S, E> {

    final override fun toString() = super.toString()
    val logger: Logger = LoggerFactory.getLogger(toString())

    @Inject
    internal lateinit var baseView: BaseView<S, A, E, NC, Parcel, VM>
    protected val viewModel get() = baseView.viewModel
    protected val disposables get() = baseView.disposables

    final override fun androidInjector() = baseView.androidInjector

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun provideViewModel(provider: ViewModelProvider): VM

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        logger.debug("onAttach: $context")
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        logger.debug("onAttachFragment: $childFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("onCreate: ${sens(savedInstanceState)}")
        baseView.stateSaver.restoreState(savedInstanceState)
        baseView.viewModel =
            provideViewModel(ViewModelProvider(this, baseView.viewModelProviderFactory))
        logger.debug("provided view model: ${baseView.viewModel}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logger.debug("onCreateView: ${sens(savedInstanceState)}")
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.debug("onViewCreated: ${sens(savedInstanceState)}")
        baseView.statesDisposables += baseView.viewModel.statesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handle, ::unexpectedError)
    }

    private fun handle(state: S) {
        baseView.stateSaver.state = state
        render(state)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logger.debug("onActivityCreated: ${sens(savedInstanceState)}")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        logger.debug("onViewStateRestored: ${sens(savedInstanceState)}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logger.debug(
            "onActivityResult: requestCode={}; resultCode={}; data={}",
            requestCode, resultCode, sens(data)
        )
    }

    override fun onStart() {
        super.onStart()
        logger.debug("onStart")
    }

    override fun onResume() {
        super.onResume()
        logger.debug("onResume")
        baseView.viewModel.eventsHolder.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        logger.debug("onPause")
        baseView.viewModel.eventsHolder.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        baseView.stateSaver.saveInstanceState(outState)
        logger.debug("onSaveInstanceState: ${sens(outState)}")
    }

    override fun onStop() {
        super.onStop()
        logger.debug("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logger.debug("onDestroyView")
        baseView.disposables.clear()
        baseView.statesDisposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.debug("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        logger.debug("onDetach")
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        requireActivity().overridePendingTransition(0, 0)
    }

    override fun render(state: S) {
        // override in nested classes if needed
    }

    override fun handle(event: E) {
        // override in nested classes if needed
    }
}
