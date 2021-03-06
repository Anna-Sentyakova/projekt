package sample.kotlin.project.presentation.core.views

import android.os.Bundle
import android.os.Parcelable
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.core.mvi.pojo.State
import javax.inject.Inject

internal class StateSaver<S : State, Parcel : Parcelable>
@Inject constructor(
    private val toParcelableMapper: Mapper<S, Parcel>,
    private val fromParcelableMapper: Mapper<Parcel, S>
) {

    companion object {
        private const val BUNDLE_SAVED_STATE = "BUNDLE_SAVED_STATE"
    }

    internal var state: S? = null

    internal fun saveInstanceState(outState: Bundle) =
        state?.let {
            outState.putParcelable(BUNDLE_SAVED_STATE, toParcelableMapper.map(it))
        }

    internal fun restoreState(savedState: Bundle?) {
        state = savedState?.getParcelable<Parcel>(BUNDLE_SAVED_STATE)
            ?.let(fromParcelableMapper::map)
    }

    internal fun stateOrDefault(defaultStateProvider: () -> S) =
        state ?: defaultStateProvider.invoke()
}
